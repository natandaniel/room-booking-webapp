'use strict';

// tag::vars[]
const React = require('react');
const ReactDOM = require('react-dom');
const client = require('./client');
// end::vars[]

// tag::app[]
class App extends React.Component {

	constructor(props) {
		super(props);
		this.state = {rooms: []};
	}

	componentDidMount() {
		client({method: 'GET', path: '/api/rooms'}).done(response => {
			this.setState({rooms: response.entity._embedded.rooms});
		});
	}

	render() {
		return (
			<RoomList rooms={this.state.rooms}/>
		)
	}
}
// end::app[]

// tag::room-list[]
class RoomList extends React.Component{
	render() {
		var rooms = this.props.rooms.map(room =>
			<Room key={room._links.self.href} room={room}/>
		);
		return (
			<table>
				<tbody>
					<tr>
						<th>Room Name</th>
					</tr>
					{rooms}
				</tbody>
			</table>
		)
	}
}
// end::room-list[]

// tag::room[]
class Room extends React.Component{
	render() {
		return (
			<tr>
				<td>{this.props.room.roomName}</td>
			</tr>
		)
	}
}
// end::room[]

// tag::render[]
ReactDOM.render(
	<App />,
	document.getElementById('react')
)
// end::render[]