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
import { getSubjects, addMark, getStudentsBySubject } from '../../api/index';

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

function TeacherAddMark() {
  const [subjects, setSubjects] = useState();
  const [selectedSubject, setSelectedSubject] = useState();
  const [students, setStudents] = useState();
  const [selectedStudent, setSelectedStudent] = useState();
  const [grade, setGrade] = useState(5);
  const [gradeWeight, setGradeWeight] = useState(1);
  const [comment, setComment] = useState('');

  const handleChangeSubject = (event) => {
    setSelectedSubject(event.target.value);
  };
  const handleChangeStudent = (event) => {
    setSelectedStudent(event.target.value);
  };

  const handleChangeGrade = (event) => {
    setGrade(event.target.value);
  };

  const handleChangeGradeWeight = (event) => {
    setGradeWeight(event.target.value);
  };

  const handleChangeComment = (event) => {
    setComment(event.target.value);
  };

  const handleClickAddMark = () => {
    addMark({
      comment,
      grade: parseInt(grade),
      gradeWeight: parseInt(gradeWeight),
      subjectId: parseInt(selectedSubject),
      userId: selectedStudent,
    }).then((res) => {
      if (res === 'Grade Added') {
        alert('Dodano ocenę');
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
              ? subjects.map((subject) => (
                  <MenuItem key={subject.subjectName} value={subject.subjectId}>
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
          <InputLabel id="demo-simple-select-label">Ocena</InputLabel>
          <Select
            labelId="term"
            id="term"
            value={grade}
            onChange={handleChangeGrade}
          >
            <MenuItem key={1} value={1}>
              {1}
            </MenuItem>
            <MenuItem key={2} value={2}>
              {2}
            </MenuItem>
            <MenuItem key={3} value={3}>
              {3}
            </MenuItem>
            <MenuItem key={4} value={4}>
              {4}
            </MenuItem>
            <MenuItem key={5} value={5}>
              {5}
            </MenuItem>
          </Select>
        </FormControl>
        <FormControl className={classes.formControl}>
          <InputLabel id="demo-simple-select-label">Waga</InputLabel>
          <Select
            labelId="term"
            id="term"
            value={gradeWeight}
            onChange={handleChangeGradeWeight}
          >
            <MenuItem key={`gradeweight-${1}`} value={1}>
              {1}
            </MenuItem>
            <MenuItem key={`gradeweight-${2}`} value={2}>
              {2}
            </MenuItem>
            <MenuItem key={`gradeweight-${3}`} value={3}>
              {3}
            </MenuItem>
            <MenuItem key={`gradeweight-${4}`} value={4}>
              {4}
            </MenuItem>
            <MenuItem key={`gradeweight-${5}`} value={5}>
              {5}
            </MenuItem>
          </Select>
        </FormControl>
      </div>
      <div className={classes.rest}>
        <FormControl className={classes.root} noValidate autoComplete="off">
          <TextField
            id="standard-basic"
            label="Komentarz"
            value={comment}
            onChange={handleChangeComment}
          />
        </FormControl>
      </div>
      <Button
        className={classes.btn}
        variant="contained"
        color="primary"
        onClick={handleClickAddMark}
      >
        Dodaj ocenę
      </Button>
      {/*<p>*/}
      {/*  selectedSubject: {selectedSubject} <br />*/}
      {/*  grade: {grade} <br />*/}
      {/*  gradeWeight: {gradeWeight} <br />*/}
      {/*  comment: {comment} <br />*/}
      {/*  userId: {selectedStudent} <br />*/}
      {/*</p>*/}
    </div>
  );
}

export default TeacherAddMark;
