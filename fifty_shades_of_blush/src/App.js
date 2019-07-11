import React, { Component } from 'react';
import './App.css';

class App extends Component {
  render() {

    const title = 'Fifty Shades Of Blush';

    const sections = [
      {
        title: 'Beauty',
        description: 'My tips and personal creations',
        url: 'localhost:3000',
      },
      {
        title: 'Fashion',
        description: 'Everything about fashion an style',
        url: 'localhost:3000',
      },
      {
        title: 'Travel',
        description: 'My journey around the world',
        url: 'localhost:3000',
      },
      {
        title: 'Lifestyle',
        description: 'How I style my life',
        url: 'localhost:3000',
      }

    ]

    return (
      <div className="App">
        <h2>{title}</h2>
        {sections.map((item) =>
          <div>
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