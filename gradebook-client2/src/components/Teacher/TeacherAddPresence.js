import {
  Button,
  Checkbox,
  FormControl,
  FormControlLabel,
  InputLabel,
  makeStyles,
  MenuItem,
  Select,
} from '@material-ui/core';
import React, { useEffect, useState } from 'react';
import {
  getSubjects,
  getStudentsBySubject,
  addPresence,
} from '../../api/index';

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
}));

export const STATUSES = {
  OBECNY: 'OBECNY',
  NIEOBECNY: 'NIEOBECNY',
  USPRAWIEDLIWIONY: 'USPRAWIEDLIWIONY',
  NIEUSPRAWIEDLIWIONY: 'NIEUSPRAWIEDLIWIONY',
};

function TeacherAddPresence() {
  const [subjects, setSubjects] = useState();
  const [selectedSubject, setSelectedSubject] = useState();
  const [students, setStudents] = useState();
  const [selectedStudent, setSelectedStudent] = useState();
  const [status, setStatus] = useState(STATUSES.obecny);
  const [presence, setPresence] = useState(true);

  const handleChangeSubject = (event) => {
    setSelectedSubject(event.target.value);
  };

  const handleChangeStudent = (event) => {
    setSelectedStudent(event.target.value);
  };

  const handleChangeStatus = (event) => {
    setStatus(event.target.value);
  };

  const handleChangePresence = (event) => {
    setPresence(event.target.checked);
  };

  const handleClickAddPresence = () => {
    addPresence({
      presence,
      status,
      subjectId: selectedSubject,
      userId: selectedStudent,
    }).then((res) => {
      if (res === 'Presence Added') {
        alert('Dodano obecność');
      } else {
        console.error(res);
      }
    });
  };

  const classes = useStyles();

  const setFetchedSubjects = () => {
    async function fetchData() {
      const data = await getSubjects();
      setSubjects(data?.subjects);
    }
    fetchData();
  };
  const setFetchedStudents = () => {
    async function fetchData() {
      const data = await getStudentsBySubject(selectedSubject);
      setStudents(data);
    }
    fetchData();
  };

  useEffect(() => {
    setFetchedSubjects();
  }, []);
  useEffect(() => {
    setFetchedStudents();
  }, [selectedSubject]);

  return (
    <div className={classes.container}>
      <div>
        <FormControl className={classes.formControl}>
          <InputLabel id="demo-simple-select-label">Przedmioty</InputLabel>
          <Select
            labelId="term"
            id="term"
            value={selectedSubject}
            onChange={handleChangeSubject}
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
          <InputLabel id="demo-simple-select-label">Studenci</InputLabel>
          <Select
            labelId="term"
            id="term"
            value={selectedStudent}
            onChange={handleChangeStudent}
          >
            {students?.length > 0
              ? students.map((student, idx) => (
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
        <FormControl className={classes.formControl}>
          <InputLabel id="demo-simple-select-label">Status</InputLabel>
          <Select
            labelId="term"
            id="term"
            value={status}
            onChange={handleChangeStatus}
          >
            {Object.keys(STATUSES).map((status, idx) => (
              <MenuItem value={status} key={`${status}--${idx}`}>
                {status}
              </MenuItem>
            ))}
          </Select>
        </FormControl>
        <FormControl className={classes.formControl}>
          <FormControlLabel
            control={
              <Checkbox
                checked={presence}
                onChange={handleChangePresence}
                name="presence"
                color="primary"
              />
            }
            label="Obecność"
          />
        </FormControl>
      </div>
      <Button
        className={classes.btn}
        variant="contained"
        color="primary"
        onClick={handleClickAddPresence}
      >
        Dodaj obecność
      </Button>
      {/*<p>*/}
      {/*  presence: {presence.toString()} <br />*/}
      {/*  status: {status} <br />*/}
      {/*  subjectId: {selectedSubject} <br />*/}
      {/*  userId: {selectedStudent} <br />*/}
      {/*</p>*/}
    </div>
  );
}

export default TeacherAddPresence;
