import {
  Button,
  FormControl,
  InputLabel,
  makeStyles,
  MenuItem,
  Select,
} from '@material-ui/core';
import React, { useEffect, useState } from 'react';
import {
  addUserToSubject,
  getAllTeachers,
  getAllUsers,
  getAllSubjects,
} from '../../api/index';

const useStyles = makeStyles((theme) => ({
  formControl: {
    margin: theme.spacing(1),
    minWidth: 200,
  },
  btn: {
    margin: theme.spacing(1),
  },
  rest: {
    marginLeft: theme.spacing(1),
  },
}));

function AdministratorAddUserToSubject() {
  const [subjects, setSubjects] = useState();
  const [users, setUsers] = useState();
  const [subjectIds, setSubjectIds] = useState([]);
  const [userId, setUserId] = useState('');

  const classes = useStyles();

  const setFetchedSubjects = () => {
    async function fetchData() {
      const data = await getAllSubjects();
      setSubjects(data);
    }
    fetchData();
  };
  const setFetchedStudents = () => {
    async function fetchData() {
      const data = await Promise.all([getAllTeachers(), getAllUsers()]);
      setUsers(data?.flat());
    }
    fetchData();
  };

  useEffect(() => {
    setFetchedSubjects();
    setFetchedStudents();
  }, []);

  const handleAddUserToSubject = () => {
    if (subjectIds?.length > 0 && userId != null) {
      addUserToSubject({
        subjectIds: subjectIds.join(','),
        userId,
      }).then((res) => {
        if (res === 'Subjects added to user') {
          alert('Uytkownik dodany do przedmiotu/ów');
        }
      });
    } else {
      alert('Nieprawidłowe dane');
    }
  };

  return (
    <div className={classes.container}>
      <FormControl className={classes.formControl}>
        <InputLabel id="demo-simple-select-label">Przedmioty</InputLabel>
        <Select
          labelId="term"
          id="term"
          multiple
          value={subjectIds}
          onChange={(e) => setSubjectIds(e.target.value)}
        >
          {subjects?.length > 0
            ? subjects.map((subject, idx) => (
                <MenuItem
                  key={subject.subjectName + idx}
                  value={subject.subjectId}
                >
                  {subject.subjectName}
                </MenuItem>
              ))
            : null}
        </Select>
      </FormControl>
      <FormControl className={classes.formControl}>
        <InputLabel id="demo-simple-select-label">Użytkownik</InputLabel>
        <Select
          labelId="usr"
          id="usr"
          value={userId}
          onChange={(e) => setUserId(e.target.value)}
        >
          {users?.length > 0
            ? users.map((user, idx) => (
                <MenuItem
                  value={user.userId}
                  key={`${user.firstName}--${idx}--${user.lastName}`}
                >
                  {`${user.firstName} ${user.lastName}`}
                </MenuItem>
              ))
            : null}
        </Select>
      </FormControl>
      <Button
        className={classes.btn}
        variant="contained"
        color="primary"
        onClick={() => handleAddUserToSubject()}
      >
        Dodaj użytkownika do przedmiotu
      </Button>
    </div>
  );
}

export default AdministratorAddUserToSubject;
