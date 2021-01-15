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
  getSubjects,
  addFinalMark,
  getStudentsBySubject,
} from '../../api/index';

const useStyles = makeStyles((theme) => ({
  formControl: {
    margin: theme.spacing(1),
    minWidth: 200,
  },
}));

function TeacherAddFinalMark() {
  const [subjects, setSubjects] = useState();
  const [selectedSubject, setSelectedSubject] = useState();
  const [students, setStudents] = useState();
  const [selectedStudent, setSelectedStudent] = useState();
  const [finalGrade, setGrade] = useState(5);
  const [semester, setSemester] = useState();

  const handleChangeSubject = (event) => {
    setSelectedSubject(event.target.value);
  };

  const handleChangeStudent = (event) => {
    setSelectedStudent(event.target.value);
  };

  const handleChangeGrade = (event) => {
    setGrade(event.target.value);
  };
  const handleChangeSemester = (event) => {
    setSemester(event.target.value);
  };

  const handleClickAddMark = () => {
    addFinalMark({
      finalGrade: parseInt(finalGrade),
      semester: semester,
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
    <div>
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
            value={finalGrade}
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
          <InputLabel id="demo-simple-select-label">Semester</InputLabel>
          <Select
            labelId="term"
            id="term"
            value={semester}
            onChange={handleChangeSemester}
          >
            <MenuItem key={'Semestr 1'} value={'Semestr 1'}>
              {'Semestr 1'}
            </MenuItem>
            <MenuItem key={'Semestr 2'} value={'Semestr 2'}>
              {'Semestr 2'}
            </MenuItem>
          </Select>
        </FormControl>
      </div>
      <Button variant="contained" color="primary" onClick={handleClickAddMark}>
        Dodaj ocenę
      </Button>
    </div>
  );
}

export default TeacherAddFinalMark;
