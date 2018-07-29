'use strict';

const React = require('react');
const ReactDOM = require('react-dom');
const when = require('when');
const client = require('./client');

const follow = require('./follow');

var stompClient = require('./websocket-listener')

const root = '/api';

class App extends React.Component {

	constructor(props) {
		super(props);
		this.state = {rooms: [], attributes: [], page: 1, pageSize: 20, links: {}};
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
					this.links = roomCollection.entity._links;
					return roomCollection;
				});
		}).then(roomCollection => {
			this.page = roomCollection.entity.page;
			return roomCollection.entity._embedded.rooms.map(room =>
					client({
						method: 'GET',
						path: room._links.self.href
					})
			);
		}).then(roomPromises => {
			return when.all(roomPromises);
		}).done(rooms => {
			this.setState({
				page: this.page,
				rooms: rooms,
				attributes: Object.keys(this.schema.properties),
				pageSize: pageSize,
				links: this.links
			});
		});
	}
	
	onNavigate(navUri) {
		client({
			method: 'GET',
			path: navUri
		}).then(roomCollection => {
			this.links = roomCollection.entity._links;
			this.page = roomCollection.entity.page;

			return roomCollection.entity._embedded.rooms.map(room =>
					client({
						method: 'GET',
						path: room._links.self.href
					})
			);
		}).then(roomPromises => {
			return when.all(roomPromises);
		}).done(rooms => {
			this.setState({
				page: this.page,
				rooms: rooms,
				attributes: Object.keys(this.schema.properties),
				pageSize: this.state.pageSize,
				links: this.links
			});
		});
	}
	
	

	componentDidMount() {
		this.loadFromServer(this.state.pageSize);

	}

	render() {
		return (
				<div>
				<RoomList rooms={this.state.rooms}
							  links={this.state.links}
							  page={this.state.page}
							  pageSize={this.state.pageSize}
							  onNavigate={this.onNavigate}/>
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
		
		var pageInfo = this.props.page.hasOwnProperty("number") ?
				<h3>Employees - Page {this.props.page.number + 1} of {this.props.page.totalPages}</h3> : null;
		
		var rooms = this.props.rooms.map(room =>
			<Room key={room.entity._links.self.href} room={room} attributes={this.props.attributes} />
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
		this.refreshCurrentPage = this.refreshCurrentPage.bind(this);
	}
	
	onBooking(meeting) {
		if(meeting.meetingBooked){
			alert('This meeting is already booked !');
		}else{
			client({method: 'POST', path: '/api/bookings/make/' + meeting.description});
		}
	}
	
	onCancelling(meeting) {
		client({method: 'PUT', path: '/api/bookings/cancel/' + meeting.description});
	}
	
	refreshCurrentPage(message) {
		client({method: 'GET', path: this.props.room.entity._links.meetings.href}).done(response => {
			this.setState({
				meetings: response.entity._embedded.meetings
			});
		});
	}
	
	componentDidMount() {
		client({method: 'GET', path: this.props.room.entity._links.meetings.href}).done(response => {
			this.setState({
				meetings: response.entity._embedded.meetings
			});
		});
		stompClient.register([
			{route: '/topic/updateMeeting', callback: this.refreshCurrentPage}
		]);
	}
	
	render() {
		return (
			<div>
				<tr>
					<td>{this.props.room.entity.roomName}</td>
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
	<App loggedInUser = {document.getElementById('user').innerHTML} />,
	document.getElementById('react')
)

