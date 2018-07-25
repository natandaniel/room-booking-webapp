'use strict';

const React = require('react');
const ReactDOM = require('react-dom');
const client = require('./client');

const follow = require('./follow');

const root = '/api';

class App extends React.Component {

	constructor(props) {
		super(props);
		this.state = {rooms: [], attributes: [], pageSize: 1, links: {}};
		this.updatePageSize = this.updatePageSize.bind(this);
		this.onNavigate = this.onNavigate.bind(this);
	}
	
	loadFromServer(pageSize) {
		follow(client, root, [
			{rel: 'rooms', params: {size: pageSize}}]
		).then(roomCollection => {
			return client({
				method: 'GET',
				path: roomCollection.entity._links.profile.href,
				headers: {'Accept': 'application/schema+json'}
			}).then(schema => {
				this.schema = schema.entity;
				return roomCollection;
			});
		}).done(roomCollection => {
			this.setState({
				rooms: roomCollection.entity._embedded.rooms,
				attributes: Object.keys(this.schema.properties),
				pageSize: pageSize,
				links: roomCollection.entity._links});
		});
	}
	
	onNavigate(navUri) {
		client({method: 'GET', path: navUri}).done(roomCollection => {
			this.setState({
				rooms: roomCollection.entity._embedded.rooms,
				attributes: this.state.attributes,
				pageSize: this.state.pageSize,
				links: roomCollection.entity._links
			});
		});
	}

	updatePageSize(pageSize) {
		if (pageSize !== this.state.pageSize) {
			this.loadFromServer(pageSize);
		}
	}

	componentDidMount() {
		this.loadFromServer(this.state.pageSize);
	}

	render() {
		return (
				<div>
				<RoomList rooms={this.state.rooms}
							  links={this.state.links}
							  pageSize={this.state.pageSize}
							  onNavigate={this.onNavigate}
							  updatePageSize={this.updatePageSize}/>
			</div>
		)
	}
}

class RoomList extends React.Component {

	constructor(props) {
		super(props);
		this.handleNavFirst = this.handleNavFirst.bind(this);
		this.handleNavPrev = this.handleNavPrev.bind(this);
		this.handleNavNext = this.handleNavNext.bind(this);
		this.handleNavLast = this.handleNavLast.bind(this);
	}

	// tag::handle-nav[]
	handleNavFirst(e){
		e.preventDefault();
		this.props.onNavigate(this.props.links.first.href);
	}

	handleNavPrev(e) {
		e.preventDefault();
		this.props.onNavigate(this.props.links.prev.href);
	}

	handleNavNext(e) {
		e.preventDefault();
		this.props.onNavigate(this.props.links.next.href);
	}

	handleNavLast(e) {
		e.preventDefault();
		this.props.onNavigate(this.props.links.last.href);
	}
	
	render() {
		var rooms = this.props.rooms.map(room =>
			<Room key={room._links.self.href} room={room} />
		);

		var navLinks = [];
		if ("first" in this.props.links) {
			navLinks.push(<button key="first" onClick={this.handleNavFirst}>&lt;&lt;</button>);
		}
		if ("prev" in this.props.links) {
			navLinks.push(<button key="prev" onClick={this.handleNavPrev}>&lt;</button>);
		}
		if ("next" in this.props.links) {
			navLinks.push(<button key="next" onClick={this.handleNavNext}>&gt;</button>);
		}
		if ("last" in this.props.links) {
			navLinks.push(<button key="last" onClick={this.handleNavLast}>&gt;&gt;</button>);
		}

		return (
			<div className="container">
				<table className="table table-striped">
					<tbody>
						<tr>
							<th>Room Name</th>
						</tr>
						{rooms}
					</tbody>
				</table>
				<div>
					{navLinks}
				</div>
			</div>
		)
	}
}

class Room extends React.Component{
	
	constructor(props) {
		super(props);
		this.state = {meetings: []};
		this.onBooking = this.onBooking.bind(this);
		this.onCancelling = this.onCancelling.bind(this);
	}
	
	onBooking(meeting) {
		if(meeting.meetingBooked){
			alert('This meeting is already booked !');
		}else{
			client({method: 'POST', path: '/api/bookings/make/' + meeting.description}).done(response => {
				client({method: 'GET', path: this.props.room._links.meetings.href}).done(response => {
					this.setState({
						meetings: response.entity._embedded.meetings
					});
				});
			});
		}
	}
	
	onCancelling(meeting) {
		client({method: 'PUT', path: '/api/bookings/cancel/' + meeting.description}).done(response => {
			client({method: 'GET', path: this.props.room._links.meetings.href}).done(response => {
				this.setState({
					meetings: response.entity._embedded.meetings
				});
			});
		});
	}
	
	componentDidMount() {
		client({method: 'GET', path: this.props.room._links.meetings.href}).done(response => {
			this.setState({
				meetings: response.entity._embedded.meetings
			});
		});
	}
	
	render() {
		return (
			<div>
				<tr>
					<td>{this.props.room.roomName}</td>
				</tr>
				<MeetingList meetings={this.state.meetings}
						  		links={this.state.links}
								onBooking={this.onBooking}
								onCancelling={this.onCancelling}
						  />
		    </div>
		)
	}
}

class MeetingList extends React.Component{
	
	constructor(props) {
		super(props);
	}
	
	render() {
		var meetings = this.props.meetings.map(meeting =>
			<Meeting key={meeting._links.self.href} meeting={meeting} onBooking={this.props.onBooking} onCancelling={this.props.onCancelling}/>
		);

		return (
			<div className="container">
				<table className="table table-striped">
					<tbody>
						<tr>
							<th>Meeting Start Time</th>
						</tr>
						{meetings}
					</tbody>
				</table>
			</div>
		)
	}
}

class Meeting extends React.Component{
	
	constructor(props) {
		super(props);
		this.handleBooking = this.handleBooking.bind(this);
		this.handleCancelling = this.handleCancelling.bind(this);
	}

	handleBooking() {
		this.props.onBooking(this.props.meeting);
	}
	
	handleCancelling() {
		this.props.onCancelling(this.props.meeting);
	}
	
	render() {
		
		if(this.props.meeting.meetingBookable){
			return (
					<tr>
						<td>{this.props.meeting.meetingStartTime}</td>
						<td>
							<button onClick={this.handleBooking}>Book</button>
						</td>
						<td>
						<button onClick={this.handleCancelling}>Cancel Booking</button>
					</td>
					</tr>
				)
		}else{
			return (
					<tr>
						<td>{this.props.meeting.meetingStartTime}</td>
						<td>
							<p>BOOKED</p>
						</td>
						<td>
						<button onClick={this.handleCancelling}>Cancel Booking</button>
					</td>
					</tr>
				)
		}
		
	}
}

ReactDOM.render(
	<App />,
	document.getElementById('react')
)

