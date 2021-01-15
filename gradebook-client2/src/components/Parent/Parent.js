import React, { useEffect } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Drawer from '@material-ui/core/Drawer';
import AppBar from '@material-ui/core/AppBar';
import CssBaseline from '@material-ui/core/CssBaseline';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import { Box } from '@material-ui/core';
import ParentMarks from './ParentMarks';
import {
  Link,
  Redirect,
  Route,
  Switch,
  useHistory,
  useLocation,
} from 'react-router-dom';
import { deleteUser, getUser } from '../../tools/user';
import ChangePassword from '../ChangePassword';
import student from '../icons/graduated.png';
import { getUserInformation } from '../../api';
import UserTermMarks from '../User/UserTermMarks';
import ParentBehaviour from './ParentBehaviour';
import ParentPresence from './ParentPresence';
import { drawerWidth } from '../../tools/helpers';

const useStyles = makeStyles((theme) => ({
  root: {
    display: 'flex',
  },
  appBar: {
    zIndex: theme.zIndex.drawer + 1,
  },
  drawer: {
    width: drawerWidth,
    flexShrink: 0,
  },
  drawerPaper: {
    width: drawerWidth,
  },
  drawerContainer: {
    overflow: 'auto',
  },
  content: {
    flexGrow: 1,
    padding: theme.spacing(3),
  },
  klasa: {
    display: 'flex',
    color: '#E6E6E6',
  },

  actionButtons: {
    marginTop: theme.spacing(0.5),
    marginBottom: theme.spacing(0.5),
    marginRight: theme.spacing(2),
  },
}));

export default function Parent() {
  const classes = useStyles();
  const [userInfo, setUserInfo] = React.useState({});
  const user = getUser();
  const history = useHistory();

  useEffect(() => {
    if (user?.userId != null) {
      getUserInformation().then((res) => {
        setUserInfo(res);
      });
    } else {
      history.push('/login');
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);
  const { pathname } = useLocation();
  return (
    <div className={classes.root}>
      <CssBaseline />
      <AppBar position="fixed" className={classes.appBar}>
        <Toolbar>
          <Typography variant="h6" noWrap>
            Elektroniczny dziennik
          </Typography>
        </Toolbar>
      </AppBar>
      <Drawer
        className={classes.drawer}
        variant="permanent"
        classes={{
          paper: classes.drawerPaper,
        }}
      >
        <Toolbar />
        <Box p={3}>
          <div className="container">
            <div className="row profile">
              <div className="col-md-3">
                <div className="profile-sidebar">
                  <div className="profile-userpic">
                    <img src={student} alt="" />
                  </div>
                  <div className="profile-usertitle">
                    <div className="profile-usertitle-name">
                      {userInfo.firstName} {userInfo.lastName}
                    </div>
                    <div className="profile-usertitle-job">{userInfo.role}</div>
                    <div className="profile-usertitle-job">
                      {userInfo.email}
                    </div>
                  </div>
                  <div className="profile-usermenu">
                    <ul className="nav">
                      <li className={pathname === '/parent' ? 'active' : ''}>
                        <Link
                          size="small"
                          className={classes.actionButtons}
                          variant="contained"
                          to="/parent"
                        >
                          Oceny
                        </Link>
                      </li>
                      <li
                        className={
                          pathname === '/parent/behaviour' ? 'active' : ''
                        }
                      >
                        <Link
                          size="small"
                          className={classes.actionButtons}
                          variant="contained"
                          to="/parent/behaviour"
                        >
                          Zachowanie
                        </Link>
                      </li>
                      <li
                        className={
                          pathname === '/parent/presence' ? 'active' : ''
                        }
                      >
                        <Link
                          size="small"
                          className={classes.actionButtons}
                          variant="contained"
                          to="/parent/presence"
                        >
                          Obecności
                        </Link>
                      </li>
                      <li
                        className={
                          pathname === '/parent/change-password' ? 'active' : ''
                        }
                      >
                        <Link
                          size="small"
                          className={classes.actionButtons}
                          variant="contained"
                          to="/parent/change-password"
                        >
                          Zmień hasło
                        </Link>
                      </li>
                      <li
                        className={
                          pathname === '/parent/term-marks' ? 'active' : ''
                        }
                      >
                        <Link
                          size="small"
                          className={classes.actionButtons}
                          variant="contained"
                          to="/parent/term-marks"
                        >
                          Sprawdź oceny semestralne
                        </Link>
                      </li>
                      <li
                        className={
                          pathname === '/administrator/change-password'
                            ? 'active'
                            : ''
                        }
                      >
                        <Link
                          size="small"
                          className={classes.actionButtons}
                          variant="contained"
                          to="/login"
                          onClick={() => {
                            deleteUser();
                          }}
                        >
                          Wyloguj
                        </Link>
                      </li>
                    </ul>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <Box></Box>
        </Box>
      </Drawer>
      <main className={classes.content}>
        <Toolbar />
        <Switch>
          <Route exact path={`/parent`}>
            <ParentMarks />
          </Route>
          <Route exact path={`/parent/behaviour`}>
            <ParentBehaviour />
          </Route>
          <Route exact path={`/parent/presence`}>
            <ParentPresence />
          </Route>
          <Route exact path={`/parent/term-marks`}>
            <UserTermMarks />
          </Route>
          <Route exact path={`/parent/change-password`}>
            <ChangePassword />
          </Route>
          <Redirect to="/home" exact />
        </Switch>
      </main>
    </div>
  );
}
