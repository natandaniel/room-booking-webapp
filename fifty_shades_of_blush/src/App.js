import React, { Component } from 'react';
import './App.css';

const sections = [
  {
    title: 'Beauty',
    description: 'My tips and personal creations',
    url: 'localhost:3000',
    id: 0
  },
  {
    title: 'Fashion',
    description: 'Everything about fashion an style',
    url: 'localhost:3000',
    id: 1
  },
  {
    title: 'Travel',
    description: 'My journey around the world',
    url: 'localhost:3000',
    id: 2
  },
  {
    title: 'Lifestyle',
    description: 'How I style my life',
    url: 'localhost:3000',
    id: 3
  }

]

class App extends Component {

  constructor(props){
    super(props);

    this.state = {
      sections, // equivalent to sections: sections,
    }
  }

  render() {

    const title = 'Fifty Shades Of Blush';

    return (
      <div className="App">
        <h2>{title}</h2>
        {this.state.sections.map((item) =>
          <div key={item.id}>
            <span>
              <a href={item.url}>{item.title}</a>
            </span>
            <p>{item.description}</p>
          </div>
          )
        }
      </div>
    );
  }
}

export default App;