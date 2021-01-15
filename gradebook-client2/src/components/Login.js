import React, { useState } from 'react';
import Avatar from '@material-ui/core/Avatar';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import TextField from '@material-ui/core/TextField';
import Link from '@material-ui/core/Link';
import Grid from '@material-ui/core/Grid';
import Box from '@material-ui/core/Box';
import LockOutlinedIcon from '@material-ui/icons/LockOutlined';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import Container from '@material-ui/core/Container';
import { LOGIN_URL } from '../api/url';
import axios from 'axios';
import { useHistory } from 'react-router-dom';
import { saveUser } from '../tools/user';

function Copyright() {
  return (
    <Typography variant="body2" color="textSecondary" align="center">
      {'Copyright © '}
      <Link color="inherit" href="">
        Online Gradebook.
      </Link>{' '}
      {new Date().getFullYear()}
      {'.'}
    </Typography>
  );
}

const useStyles = makeStyles((theme) => ({
  paper: {
    marginTop: theme.spacing(8),
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
  },
  avatar: {
    margin: theme.spacing(1),
    backgroundColor: theme.palette.secondary.main,
  },
  form: {
    width: '100%', // Fix IE 11 issue.
    marginTop: theme.spacing(1),
  },
  submit: {
    margin: theme.spacing(3, 0, 2),
  },
}));

export default function SignIn() {
  const classes = useStyles();
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const history = useHistory();
  const hadleOnChangeUsername = (e) => {
    setUsername(e.target.value);
  };

  const hadleOnChangePassword = (e) => {
    setPassword(e.target.value);
  };

  const handleOnSave = (e) => {
    e.preventDefault();
    axios
      .post(LOGIN_URL, {
        username,
        password,
      })
      .then((response) => {
        if (response.status === 200) {
          saveUser(response.data);
          if (response.data.role === 'ROLE_TEACHER') {
            history.push('/teacher');
          } else if (response.data.role === 'ROLE_STUDENT') {
            history.push('/user');
          } else if (response.data.role === 'ROLE_PARENT') {
            history.push('/parent');
          } else if (response.data.role === 'ROLE_ADMIN') {
            history.push('/administrator');
          }
        } else {
          console.error('NO ROLES KNOWN');
        }
      })
      .catch((err) => {
        console.error(err);
        alert('Bad Credentials');
      });
  };

  return (
    <Container component="main" maxWidth="xs">
      <CssBaseline />
      <div className={classes.paper}>
        <Avatar className={classes.avatar}>
          <LockOutlinedIcon />
        </Avatar>
        <Typography component="h1" variant="h5">
          Zaloguj się jako: {username}
        </Typography>
        <form className={classes.form} noValidate>
          <TextField
            variant="outlined"
            margin="normal"
            required
            fullWidth
            id="username"
            label="Username"
            name="username"
            autoComplete="username"
            autoFocus
            onChange={hadleOnChangeUsername}
          />
          <TextField
            variant="outlined"
            margin="normal"
            required
            fullWidth
            name="password"
            label="Password"
            type="password"
            id="password"
            autoComplete="current-password"
            onChange={hadleOnChangePassword}
          />
          <Button
            type="submit"
            fullWidth
            variant="contained"
            color="primary"
            className={classes.submit}
            onClick={handleOnSave}
          >
            Zaloguj się
          </Button>
          <Grid container>
            <Grid item xs>
            </Grid>
            <Grid item></Grid>
          </Grid>
        </form>
      </div>
      <Box mt={8}>
        <Copyright />
      </Box>
    </Container>
  );
}
