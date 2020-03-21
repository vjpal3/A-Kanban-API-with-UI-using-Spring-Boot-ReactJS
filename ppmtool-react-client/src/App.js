import React, { Component } from 'react';
import { BrowserRouter as Router, Route } from 'react-roter-dom';
import './App.css';
import Dashboard from './components/Dashboard';
import Header from './components/Layout/Header';
import 'bootstrap/dist/css/bootstrap.min.css';

class App extends Component {
  render() {
    return (
      <Router>
        <div className="App">
          <Header />
          <Dashboard />
        </div>
      </Router>
    );
  }
}

export default App;
