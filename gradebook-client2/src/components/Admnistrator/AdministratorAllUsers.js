import {
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
  makeStyles,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  TextField,
} from '@material-ui/core';
import React, { useEffect, useState } from 'react';
import {
  getAllUsers,
  removeUser,
  resetPassword,
  getUserInformation,
  updateUserInformation,
} from '../../api/index';

const useStyles = makeStyles((theme) => ({
  formControl: {
    margin: theme.spacing(1),
    minWidth: 200,
  },
  btn: {
    margin: theme.spacing(1),
  },
  TextField: {
    margin: theme.spacing(1),
  },
}));

function AdministratorAllUsers() {
  const [users, setUsers] = useState();
  const [email, setEmail] = useState('');
  const [username, setUsername] = useState('');
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [open, setOpen] = React.useState(false);
  const [userId, setUserId] = React.useState('');

  const classes = useStyles();

  const handleClickEditUser = (userId) => {
    setUserId(userId);
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const setFetchedStudents = () => {
    async function fetchData() {
      const data = await getAllUsers();
      setUsers(data);
    }
    fetchData();
  };

  const handleRemoveUser = (userId) => {
    removeUser(userId).then((res) => {
      alert('Usunięto użytkownika');
      setFetchedStudents();
    });
  };

  const handleResetPassword = (userId) => {
    resetPassword(userId).then((res) => {
      if (res === 'User password reset') {
        alert('Zmieniono hasło');
      }
    });
  };

  const handleEditUser = () => {
    updateUserInformation(userId, {
      email,
      username,
      firstName,
      lastName,
    }).then((res) => {
      if (res) {
        alert('Zaktualizowano');
        setOpen(false);
        setFetchedStudents();
      }
    });
  };

  useEffect(() => {
    setFetchedStudents();
  }, []);

  useEffect(() => {
    if (open && userId != null) {
      getUserInformation(userId).then((res) => {
        const { username, email, firstName, lastName } = res;
        setUsername(username);
        setEmail(email);
        setFirstName(firstName);
        setLastName(lastName);
      });
    }
  }, [userId]);

  return users == null || users?.length === 0 ? null : (
    <div className={classes.container}>
      <TableContainer component={Paper}>
        <Table className={classes.table} aria-label="simple table">
          <TableHead>
            <TableRow>
              <TableCell align="right">userId</TableCell>
              <TableCell align="right">classId</TableCell>
              <TableCell align="right">firstName</TableCell>
              <TableCell align="right">lastName</TableCell>
              <TableCell align="right">email</TableCell>
              <TableCell align="right">role</TableCell>
              <TableCell align="right">childrenIds</TableCell>
              <TableCell align="right">className</TableCell>
              <TableCell align="right"></TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {users?.map((row) => (
              <TableRow key={row.userId}>
                <TableCell align="right">{row.userId}</TableCell>
                <TableCell align="right">{row.classId}</TableCell>
                <TableCell align="right">{row.firstName}</TableCell>
                <TableCell align="right">{row.lastName}</TableCell>
                <TableCell align="right">{row.email}</TableCell>
                <TableCell align="right">{row.role}</TableCell>
                <TableCell align="right">{row.childrenIds}</TableCell>
                <TableCell align="right">{row.className}</TableCell>
                <TableCell align="right">
                  <Button
                    className={classes.btn}
                    variant="contained"
                    color="secondary"
                    onClick={() => handleRemoveUser(row.userId)}
                  >
                    Usuń
                  </Button>
                  <Button
                    className={classes.btn}
                    variant="contained"
                    color="primary"
                    onClick={() => handleResetPassword(row.userId)}
                  >
                    Reset hasła
                  </Button>
                  <Button
                    className={classes.btn}
                    variant="contained"
                    color="primary"
                    onClick={() => handleClickEditUser(row.userId)}
                  >
                    Edytuj dane
                  </Button>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>

      <Dialog
        open={open}
        onClose={handleClose}
        aria-labelledby="alert-dialog-title"
        aria-describedby="alert-dialog-description"
      >
        <DialogTitle id="alert-dialog-title">{'Edytuj obecność'}</DialogTitle>
        <DialogContent>
          <DialogContentText
            style={{ width: '500px', display: 'flex', flexDirection: 'column' }}
            id="alert-dialog-description"
          >
            <form className={classes.dialogForm} noValidate autoComplete="off">
              <TextField
                id="email"
                label="email"
                value={email}
                fullWidth
                className={classes.TextField}
                onChange={(e) => setEmail(e.target.value)}
              />
              <TextField
                id="username"
                label="username"
                value={username}
                fullWidth
                className={classes.TextField}
                onChange={(e) => setUsername(e.target.value)}
              />
              <TextField
                id="firstName"
                label="firstName"
                value={firstName}
                fullWidth
                className={classes.TextField}
                onChange={(e) => setFirstName(e.target.value)}
              />
              <TextField
                id="lastName"
                label="lastName"
                value={lastName}
                fullWidth
                className={classes.TextField}
                onChange={(e) => setLastName(e.target.value)}
              />
            </form>

            <Button
              variant="contained"
              color="primary"
              onClick={handleEditUser}
            >
              Zapisz
            </Button>
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose} color="primary">
            Zamknij
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}

export default AdministratorAllUsers;
