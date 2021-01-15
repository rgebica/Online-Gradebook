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
  useRouteMatch,
} from 'react-router-dom';
import { deleteUser, getUser } from '../../tools/user';
import ChangePassword from '../ChangePassword';
import student from '../icons/graduated.png';
import { getUserInformation } from '../../api';
import AdministratorAllUsers from './AdministratorAllUsers';
import AdministratorAddUser from './AdministratorAddUser';
import AdministratorAddSubject from './AdministratorAddSubject';
import AdministratorAddClass from './AdministratorAddClass';
import { drawerWidth } from '../../tools/helpers';
import AdministratorAddUserToSubject from './AdministratorAddUserToSubject';

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

export default function Administrator() {
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
                      {'Administrator'}
                    </div>
                    <div className="profile-usertitle-job">{userInfo.role}</div>
                    <div className="profile-usertitle-job">
                      {userInfo.email}
                    </div>
                  </div>
                  <div className="profile-usermenu">
                    <ul className="nav">
                      <li
                        className={
                          pathname === '/administrator' ? 'active' : ''
                        }
                      >
                        <Link
                          size="small"
                          className={classes.actionButtons}
                          variant="contained"
                          to="/administrator"
                        >
                          Użytkownicy
                        </Link>
                      </li>
                      <li
                        className={
                          pathname === '/administrator/add-user' ? 'active' : ''
                        }
                      >
                        <Link
                          size="small"
                          className={classes.actionButtons}
                          variant="contained"
                          to="/administrator/add-user"
                        >
                          Dodaj użytkownika
                        </Link>
                      </li>
                      <li
                        className={
                          pathname === '/administrator/add-subject'
                            ? 'active'
                            : ''
                        }
                      >
                        <Link
                          size="small"
                          className={classes.actionButtons}
                          variant="contained"
                          to="/administrator/add-subject"
                        >
                          Dodaj Przedmiot
                        </Link>
                      </li>
                      <li
                        className={
                          pathname === '/administrator/add-class'
                            ? 'active'
                            : ''
                        }
                      >
                        <Link
                          size="small"
                          className={classes.actionButtons}
                          variant="contained"
                          to="/administrator/add-class"
                        >
                          Dodaj klasę
                        </Link>
                      </li>
                      <li
                        className={
                          pathname === '/administrator/add-user-to-subject'
                            ? 'active'
                            : ''
                        }
                      >
                        <Link
                          size="small"
                          className={classes.actionButtons}
                          variant="contained"
                          to="/administrator/add-user-to-subject"
                        >
                          Dodaj ucznia do przedmiotu
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
                          to="/administrator/change-password"
                        >
                          Zmień hasło
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
          <Route exact path={'/administrator'}>
            <AdministratorAllUsers />
          </Route>
          <Route exact path={`/administrator/add-user`}>
            <AdministratorAddUser />
          </Route>
          <Route exact path={`/administrator/add-subject`}>
            <AdministratorAddSubject />
          </Route>
          <Route exact path={`/administrator/add-class`}>
            <AdministratorAddClass />
          </Route>
          <Route exact path={`/administrator/add-user-to-subject`}>
            <AdministratorAddUserToSubject />
          </Route>
          <Route exact path={`/administrator/change-password`}>
            <ChangePassword />
          </Route>
          <Redirect to="/home" exact />
        </Switch>
      </main>
    </div>
  );
}
