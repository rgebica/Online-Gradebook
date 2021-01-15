import React from 'react';
import { BrowserRouter as Router, Switch, Route } from 'react-router-dom';
import Login from './components/Login';
import CreateUser from './components/CreateUser';
import User from './components/User/User';
import Teacher from './components/Teacher/Teacher';
import Parent from './components/Parent/Parent';
import PrivateRoute from './components/PrivateRoute';
import Administrator from './components/Admnistrator/Administrator';

export default function App() {
  return (
    <Router>
      <div className={'App'}>
        <nav></nav>

        {/* A <Switch> looks through its children <Route>s and
            renders the first one that matches the current URL. */}
        <Switch>
          <PrivateRoute exact path="/">
            <Login />
          </PrivateRoute>
          <Route path="/login">
            <Login />
          </Route>
          {/* <PrivateRoute exact path="/users">
            <User />
          </PrivateRoute> */}
          <PrivateRoute path="/create-User">
            <CreateUser />
          </PrivateRoute>
          <PrivateRoute path="/user" component={User} />
          <PrivateRoute path="/teacher" component={Teacher} />
          <PrivateRoute path="/parent" component={Parent} />
          <PrivateRoute path="/administrator" component={Administrator} />
          <Route path="/home">
            <Home />
          </Route>
        </Switch>
      </div>
    </Router>
  );
}

function Home() {
  return (
    <h2>
      <a href="/login">Przejdz do logowania</a>
    </h2>
  );
}
