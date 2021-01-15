import {
  FormControl,
  InputLabel,
  makeStyles,
  MenuItem,
  Select,
} from '@material-ui/core';
import React, { useEffect, useState } from 'react';
import { getSubjects, getChildrens } from '../../api/index';
import UserPresence from '../User/UserPresence';

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

function ParentPresence() {
  const [subjects, setSubjects] = useState();
  const [selectedSubject, setSelectedSubject] = useState();
  const [students, setStudents] = useState();
  const [selectedStudent, setSelectedStudent] = useState();

  const handleChangeSubject = (event) => {
    setSelectedSubject(event.target.value);
  };
  const handleChangeStudent = (event) => {
    setSelectedStudent(event.target.value);
  };

  const classes = useStyles();

  const setFetchedSubjects = () => {
    async function fetchData() {
      const data = await getSubjects(selectedStudent);
      setSubjects(data?.subjects);
    }
    fetchData();
  };
  const setFetchedStudents = () => {
    async function fetchData() {
      const data = await getChildrens();
      setStudents(data?.children);
    }
    fetchData();
  };

  useEffect(() => {
    setFetchedSubjects();
  }, [selectedStudent]);
  useEffect(() => {
    setFetchedStudents();
  }, [selectedSubject]);

  return (
    <div className={classes.container}>
      <div>
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
          <InputLabel id="demo-simple-select-label">Przedmioty</InputLabel>
          <Select
            labelId="term"
            id="term"
            value={selectedSubject}
            onChange={handleChangeSubject}
          >
            {subjects?.length > 0
              ? subjects.map((subject) => (
                  <MenuItem key={subject.subjectName} value={subject.subjectId}>
                    {subject.subjectName}
                  </MenuItem>
                ))
              : null}
          </Select>
        </FormControl>
      </div>
      {selectedStudent != null && selectedSubject != null && (
        <UserPresence
          subjectId={selectedSubject}
          userId={selectedStudent}
          editable={true}
        />
      )}
    </div>
  );
}

export default ParentPresence;
