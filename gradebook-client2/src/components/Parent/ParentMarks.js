import {
  Button,
  FormControl,
  InputLabel,
  makeStyles,
  MenuItem,
  Paper,
  Select,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
} from '@material-ui/core';
import React, { useEffect, useState } from 'react';
import {
  getChildrens,
  getSubjects,
  getUserSubjectsWithGrades,
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

function TeacherMarks() {
  const [subjects, setSubjects] = useState();
  const [selectedSubject, setSelectedSubject] = useState();
  const [marks, setMarks] = useState([]);
  const [students, setStudents] = useState([]);
  const [selectedStudent, setSelectedStudent] = useState();

  const handleChangeSubject = (event) => {
    setSelectedSubject(event.target.value);
  };

  const classes = useStyles();

  const setFetchedSubjects = () => {
    async function fetchData() {
      const data = await getSubjects(selectedStudent);
      setSubjects(data?.subjects);
    }
    fetchData();
  };
  const setFetchedMarks = () => {
    async function fetchData() {
      const data = await getUserSubjectsWithGrades(selectedStudent);
      const grades = data?.filter((d) => d.subjectId === selectedSubject);
      setMarks(grades);
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
    setFetchedStudents();
  }, []);
  useEffect(() => {
    setFetchedSubjects();
  }, [selectedStudent]);
  useEffect(() => {
    setFetchedMarks();
  }, [selectedSubject]);

  return (
    <div className={classes.container}>
      <div>
        <FormControl className={classes.formControl}>
          <InputLabel id="demo-simple-select-label">Dzieci</InputLabel>
          <Select
            labelId="students"
            id="students"
            value={selectedStudent}
            onChange={(e) => setSelectedStudent(e.target.value)}
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

        <TableContainer component={Paper}>
          <Table className={classes.table} aria-label="simple table">
            <TableHead>
              <TableRow>
                <TableCell align="right">Imię i Nazwisko</TableCell>
                <TableCell align="right">waga 5</TableCell>
                <TableCell align="right">waga 4</TableCell>
                <TableCell align="right">waga 3</TableCell>
                <TableCell align="right">waga 2</TableCell>
                <TableCell align="right">waga 1</TableCell>
                <TableCell align="right">średnia ważona</TableCell>
                <TableCell align="right">Ocena początkowa</TableCell>
                <TableCell align="right">Średnia roczna</TableCell>
                <TableCell align="right">Ocena końcowa</TableCell>
                <TableCell align="right"></TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {marks?.length > 0 &&
                marks?.map((mark, markIdx) => (
                  <TableRow key={`${markIdx}--${mark.subjectName}`}>
                    <TableCell align="right">{`${mark.firstName} ${mark.lastName}`}</TableCell>
                    {[5, 4, 3, 2, 1].map((row) => (
                      <>
                        <TableCell align="right">
                          {mark.grades
                            ?.filter(({ gradeWeight }) => gradeWeight === row)
                            .map(({ grade }) => grade)
                            ?.join(', ')}
                        </TableCell>
                      </>
                    ))}
                    <TableCell align="right">{mark.subjectAverage}</TableCell>
                    <TableCell align="right">{mark.semesterGrade}</TableCell>
                    <TableCell align="right">{mark.yearAverage}</TableCell>
                    <TableCell align="right">{mark.finalGrade}</TableCell>
                    <TableCell align="right">{mark.finalGrade}</TableCell>
                    <TableCell align="center">
                      <Button
                        small
                        variant="outlined"
                        color="primary"
                        onClick={() => {
                          // setEditablePresence(row);
                          // handleClickOpen();
                        }}
                      >
                        Usuń
                      </Button>
                    </TableCell>
                  </TableRow>
                ))}
            </TableBody>
          </Table>
        </TableContainer>
      </div>
    </div>
  );
}

export default TeacherMarks;
