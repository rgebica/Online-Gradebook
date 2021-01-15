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
import { getStudents, addBehaviour } from '../../api/index';

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

function TeacherAddBehaviour() {
  const [students, setStudents] = useState();
  const [selectedStudent, setSelectedStudent] = useState();
  const [grade, setGrade] = useState(5);
  const [description, setDescription] = useState('');

  const handleChangeDescription = (event) => {
    setDescription(event.target.value);
  };
  const handleChangeStudent = (event) => {
    setSelectedStudent(event.target.value);
  };

  const handleChangeGrade = (event) => {
    setGrade(event.target.value);
  };

  const handleClickAddMark = () => {
    addBehaviour({
      description,
      grade: parseInt(grade),
      userId: selectedStudent,
    }).then((res) => {
      if (res === 'Behaviour Added') {
        alert('Dodano zachowanie');
      } else {
        console.error(res);
      }
    });
  };

  const classes = useStyles();

  const setFetchedStudents = () => {
    async function fetchData() {
      const data = await getStudents();
      if (data?.length > 0 && data[0] && data[0].students) {
        setStudents(data[0].students);
      }
    }
    fetchData();
  };

  useEffect(() => {
    setFetchedStudents();
  }, []);

  return (
    <div className={classes.container}>
      <div>
        <FormControl className={classes.formControl}>
          <InputLabel id="demo-simple-select-label">Studenci</InputLabel>
          <Select
            labelId="students"
            id="students"
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
      </div>
      <div className={classes.rest}>
        <FormControl className={classes.root} noValidate autoComplete="off">
          <TextField
            id="standard-basic"
            label="Opis"
            value={description}
            onChange={handleChangeDescription}
          />
        </FormControl>
      </div>
      <Button
        className={classes.btn}
        variant="contained"
        color="primary"
        onClick={handleClickAddMark}
      >
        Dodaj zachowanie
      </Button>
      {/*<p>*/}
      {/*  description: {description} <br />*/}
      {/*  grade: {grade} <br />*/}
      {/*  userId: {selectedStudent} <br />*/}
      {/*</p>*/}
    </div>
  );
}

export default TeacherAddBehaviour;
