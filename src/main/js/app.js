'use strict';


const React = require('react');
const ReactDOM = require('react-dom');
const client = require('./client');

const follow = require('./follow');

const root = '/api';

class App extends React.Component {

	constructor(props) {
		super(props);
		this.state = {rooms: [], meetings: [], attributes: [], pageSize: 2, links: {}};
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
		this.handleNavMeetings = this.handleNavMeetings.bind(this);
		this.handleInput = this.handleInput.bind(this);
	}

	// tag::handle-page-size-updates[]
	handleInput(e) {
		e.preventDefault();
		var pageSize = ReactDOM.findDOMNode(this.refs.pageSize).value;
		if (/^[0-9]+$/.test(pageSize)) {
			this.props.updatePageSize(pageSize);
		} else {
			ReactDOM.findDOMNode(this.refs.pageSize).value =
				pageSize.substring(0, pageSize.length - 1);
		}
	}
	// end::handle-page-size-updates[]

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

	handleNavMeetings(e) {
		e.preventDefault();
		this.props.onNavigate(this.props.links.meetings.href);
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
		if ("meetings" in this.props.links) {
			navLinks.push(<button key="meetings" onClick={this.handleNavMeetings}>meetings</button>);
		}

		return (
			<div className="container">
				<input ref="pageSize" defaultValue={this.props.pageSize} onInput={this.handleInput}/>
				<table className="table table-striped">
					<tbody>
						<tr>
							<th>Room Name</th>
							<th></th>
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
	}

	render() {
		return (
			<tr>
				<td>{this.props.room.roomName}</td>
				<td>{this.props.room.meetings}</td>
			</tr>
		)
	}
}

class Meeting extends React.Component{
	render() {
		return (
			<tr>
				<td>{this.props.meeting.meetingStartTime}</td>
			</tr>
		)
	}
}

ReactDOM.render(
	<App />,
	document.getElementById('react')
)

