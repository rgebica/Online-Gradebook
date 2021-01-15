import {
  Button,
  FormControl,
  InputLabel,
  makeStyles,
  MenuItem,
  Select,
  TextField,
} from '@material-ui/core';
import React, { useEffect, useState } from 'react';
import { getAllClasses, addUser, getStudents } from '../../api/index';

import { ROLES } from '../../tools/helpers';

const useStyles = makeStyles((theme) => ({
  formControl: {
    margin: theme.spacing(1),
    minWidth: 200,
  },
  btn: {
    margin: theme.spacing(2),
  },
  rest: {
    marginLeft: theme.spacing(1),
  },
  TextField: {
    margin: theme.spacing(1),
  },
}));

function AdministratorAddUser() {
  const [childrenIds, setChildrenIds] = useState([]);
  const [classId, setClassId] = useState('');
  const [email, setEmail] = useState('');
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [role, setRole] = useState('');
  const [username, setUsername] = useState('');
  const [allClasses, setAllClasses] = useState([]);
  const [allUsers, setAllUsers] = useState([]);

  const handleClickAddUser = () => {
    let obj = {
      classId,
      email,
      firstName,
      lastName,
      role,
      username,
    };
    if (role === ROLES.PARENT) {
      obj.childrenIds = childrenIds.toString();
    }
    addUser({ ...obj }).then((res) => {
      if (res === 'User Created') {
        alert('Dodano użytkownika');
        setFetchedUsers();
      } else {
        console.error(res);
      }
    });
  };

  const classes = useStyles();

  const setFetchedUsers = () => {
    async function fetchData() {
      const data = await getStudents();
      if (data?.length > 0 && data[0] && data[0].students) {
        setAllUsers(data[0].students);
      }
    }
    fetchData();
  };

  const setFetchedClasses = () => {
    async function fetchData() {
      const data = await getAllClasses();
      setAllClasses(data);
    }
    fetchData();
  };

  useEffect(() => {
    setFetchedClasses();
    setFetchedUsers();
  }, []);

  return (
    <div className={classes.container}>
      <div>
        {role === ROLES.PARENT && (
          <FormControl className={classes.formControl}>
            <InputLabel id="demo-simple-select-label">Dzieci</InputLabel>
            <Select
              multiple
              labelId="term"
              id="term"
              value={childrenIds}
              onChange={(e) => setChildrenIds(e.target.value)}
            >
              {allUsers?.length > 0
                ? allUsers.map((student, idx) => (
                    <MenuItem
                      value={student.userId}
                      key={`${student.firstName}--${idx}--${student.lastName}`}
                    >
                      {`${student.firstName} ${student.lastName}`}
                    </MenuItem>
                  ))
                : null}
            </Select>
          </FormControl>
        )}

        <FormControl className={classes.formControl}>
          <InputLabel id="demo-simple-select-label">Rola</InputLabel>
          <Select
            labelId="term"
            id="term"
            value={role}
            onChange={(e) => setRole(e.target.value)}
          >
            {Object.keys(ROLES).map((role, idx) => (
              <MenuItem value={ROLES[role]} key={`${role}--${idx}`}>
                {role}
              </MenuItem>
            ))}
          </Select>
        </FormControl>

        <FormControl className={classes.formControl}>
          <InputLabel id="demo-simple-select-label">Klasa</InputLabel>
          <Select
            labelId="term"
            id="term"
            value={classId}
            onChange={(e) => setClassId(e.target.value)}
          >
            {allClasses?.length > 0
              ? allClasses
                  .filter(
                    (item) =>
                      (role === ROLES.STUDENT && item?.className !== 'None') ||
                      ((role === ROLES.TEACHER || role === ROLES.PARENT) &&
                        item?.className === 'None')
                  )
                  .map((userClass, idx) => (
                    <MenuItem
                      value={userClass.classId}
                      key={`${userClass.classId}--${idx}`}
                    >
                      {userClass.className}
                    </MenuItem>
                  ))
              : null}
          </Select>
        </FormControl>
        <TextField
          className={classes.TextField}
          autoComplete="fname"
          name="userName"
          variant="outlined"
          required
          fullWidth
          id="username"
          label="Username"
          autoFocus
          onChange={(e) => setUsername(e.target.value)}
        />
        <TextField
          className={classes.TextField}
          autoComplete="fname"
          name="firstName"
          variant="outlined"
          required
          fullWidth
          id="firstName"
          label="First Name"
          autoFocus
          onChange={(e) => setFirstName(e.target.value)}
        />
        <TextField
          className={classes.TextField}
          variant="outlined"
          required
          fullWidth
          id="lastName"
          label="Last Name"
          name="lastName"
          autoComplete="lname"
          onChange={(e) => setLastName(e.target.value)}
        />
        <TextField
          className={classes.TextField}
          variant="outlined"
          required
          fullWidth
          id="email"
          label="Email Address"
          name="email"
          autoComplete="email"
          onChange={(e) => setEmail(e.target.value)}
        />
      </div>
      <Button
        className={classes.btn}
        variant="contained"
        color="primary"
        onClick={handleClickAddUser}
      >
        Dodaj użytkownika
      </Button>
    </div>
  );
}

export default AdministratorAddUser;
