import React, { useEffect } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Drawer from '@material-ui/core/Drawer';
import AppBar from '@material-ui/core/AppBar';
import CssBaseline from '@material-ui/core/CssBaseline';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import { Box } from '@material-ui/core';
import {
  Link,
  Redirect,
  Route,
  Switch,
  useHistory,
  useLocation,
} from 'react-router-dom';
import { deleteUser, getUser } from '../../tools/user';
import student from '../icons/graduated.png';
import { getUserInformation } from '../../api';
import TeacherAddMark from './TeacherAddMark';
import TeacherAddFinalMark from './TeacherAddFinalMark';
import TeacherAddPresence from './TeacherAddPresence';
import TeacherAddBehaviour from './TeacherAddBehaviour';
import TeacherPresence from './TeacherPresence';
import TeacherMarks from './TeacherMarks';
import TeacherBehaviour from './TeacherBehaviours';
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

export default function Teacher() {
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
                      <li className={pathname === '/teacher' ? 'active' : ''}>
                        <Link
                          size="small"
                          className={classes.actionButtons}
                          variant="contained"
                          to="/teacher"
                        >
                          Pokaż Oceny
                        </Link>
                      </li>
                      <li
                        className={
                          pathname === '/teacher/presence' ? 'active' : ''
                        }
                      >
                        <Link
                          size="small"
                          className={classes.actionButtons}
                          variant="contained"
                          to="/teacher/presence"
                        >
                          Pokaż obecności
                        </Link>
                      </li>

                      {
                        <li
                          className={
                            pathname === '/teacher/behaviour' ? 'active' : ''
                          }
                        >
                          <Link
                            size="small"
                            className={classes.actionButtons}
                            variant="contained"
                            to="/teacher/behaviour"
                          >
                            Pokaż zachowanie
                          </Link>
                        </li>
                        /* <li
                        className={
                          pathname === '/user/change-password' ? 'active' : ''
                        }
                      >
                        <Link
                          size="small"
                          className={classes.actionButtons}
                          variant="contained"
                          to="/user/change-password"
                        >
                          Zmień hasło
                        </Link>
                      </li>
                      <li
                        className={
                          pathname === '/user/term-marks' ? 'active' : ''
                        }
                      >
                        <Link
                          size="small"
                          className={classes.actionButtons}
                          variant="contained"
                          to="/user/term-marks"
                        >
                          Sprawdź oceny semestralne
                        </Link>
                      </li> 
                    */
                      }

                      <li
                        className={
                          pathname === '/teacher/add-mark' ? 'active' : ''
                        }
                      >
                        <Link
                          size="small"
                          className={classes.actionButtons}
                          variant="contained"
                          to="/teacher/add-mark"
                        >
                          Dodaj ocenę
                        </Link>
                      </li>
                      <li
                        className={
                          pathname === '/teacher/add-final-mark' ? 'active' : ''
                        }
                      >
                        <Link
                          size="small"
                          className={classes.actionButtons}
                          variant="contained"
                          to="/teacher/add-final-mark"
                        >
                          Dodaj ocenę końcową
                        </Link>
                      </li>
                      <li
                        className={
                          pathname === '/teacher/add-presence' ? 'active' : ''
                        }
                      >
                        <Link
                          size="small"
                          className={classes.actionButtons}
                          variant="contained"
                          to="/teacher/add-presence"
                        >
                          Dodaj obecność
                        </Link>
                      </li>
                      <li
                        className={
                          pathname === '/teacher/add-behaviour' ? 'active' : ''
                        }
                      >
                        <Link
                          size="small"
                          className={classes.actionButtons}
                          variant="contained"
                          to="/teacher/add-behaviour"
                        >
                          Dodaj zachowanie
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
          <Route exact path={'/teacher'}>
            <TeacherMarks />
            {/* main page */}
          </Route>
          <Route exact path={`/teacher/marks`}>
            <TeacherMarks />
          </Route>
          <Route exact path={`/teacher/behaviour`}>
            <TeacherBehaviour />
          </Route>
          <Route exact path={`/teacher/add-mark`}>
            <TeacherAddMark />
          </Route>
          <Route exact path={`/teacher/add-final-mark`}>
            <TeacherAddFinalMark />
          </Route>
          <Route exact path={`/teacher/add-presence`}>
            <TeacherAddPresence />
          </Route>
          <Route exact path={`/teacher/add-behaviour`}>
            <TeacherAddBehaviour />
          </Route>
          <Route exact path={`/teacher/presence`}>
            <TeacherPresence />
          </Route>
          <Redirect to="/home" exact />
        </Switch>
      </main>
    </div>
  );
}
