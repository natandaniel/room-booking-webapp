import React, { Component } from 'react'; 
import './App.css';

class App extends Component {
  render() {

    const title = 'Fifty Shades Of Blush';

    const sections = [
      {
        title : 'Beauty',
        description : 'My tips and personal creations'
      },
      {
        title : 'Fashion',
        description : 'Everything about fashion an style'
      },
      {
        title : 'Travel',
        description : 'My journey around the world'
      },
      {
        title : 'Lifestyle',
        description : 'How I style my life'
      }

    ]

    return (
      <div className="App">
        <h2>{title}</h2>
        {sections.map((item)=><div>{item.title}</div>)}
      </div>
    );
  }
}

export default App;