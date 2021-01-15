import React, { useState } from 'react';
import Avatar from '@material-ui/core/Avatar';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import TextField from '@material-ui/core/TextField';
import LockOutlinedIcon from '@material-ui/icons/LockOutlined';
import { makeStyles } from '@material-ui/core/styles';
import Container from '@material-ui/core/Container';
import axios from 'axios';
import { getUser } from '../tools/user';
import { API_HOST } from '../api/url';
import Typography from "@material-ui/core/Typography";

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

export default function ChangePassword() {
  const classes = useStyles();
  const [oldPassword, setOldPassword] = useState('');
  const [newPassword, setNewPassword] = useState('');

  const hadleOnChangeOldPassword = (e) => {
    setOldPassword(e.target.value);
  };

  const hadleOnChangeNewPassword = (e) => {
    setNewPassword(e.target.value);
  };

  const handleOnSave = (e) => {
    e.preventDefault();
    const user = getUser();
    axios
      .put(API_HOST + '/user-password/' + user.userId, {
        newPassword,
        oldPassword,
      })
      .then((response) => {
        if (response?.status === 200) {
          alert('Hasło zmienione');
        }
        alert(response);
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
        <Typography component="h1" variant="h5">
          Zmień hasło
        </Typography>
        <form className={classes.form} noValidate>
          <TextField
            variant="outlined"
            margin="normal"
            required
            fullWidth
            id="newPassword"
            label="
            Nowe hasło"
            name="newPassword"
            autoComplete="newPassword"
            autoFocus
            onChange={hadleOnChangeNewPassword}
          />
          <TextField
            variant="outlined"
            margin="normal"
            required
            fullWidth
            name="Oldpassword"
            label="Stare hasło"
            type="Oldpassword"
            id="Oldpassword"
            autoComplete="current-password"
            onChange={hadleOnChangeOldPassword}
          />
          <Button
            type="submit"
            fullWidth
            variant="contained"
            color="primary"
            className={classes.submit}
            onClick={handleOnSave}
          >
            Zmień
          </Button>
        </form>
      </div>
    </Container>
  );
}
