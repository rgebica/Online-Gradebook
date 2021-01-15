import {
  Box,
  FormControl,
  InputLabel,
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
import { makeStyles } from '@material-ui/core/styles';
import { getSemesterGrades } from '../../api';

const useStyles = makeStyles((theme) => ({
  formControl: {
    margin: theme.spacing(1),
    minWidth: 200,
  },
}));

const TERMS = {
  term1: 'Semestr 1',
  term2: 'Semestr 2',
};

function UserTermMarks() {
  const [term, setTerm] = useState(TERMS.term1);
  const [userGrades, setUserGrades] = useState([]);
  const handleChange = (event) => {
    setTerm(event.target.value);
  };

  const classes = useStyles();

  const setMarksFromTerm = () => {
    async function fetchData() {
      // You can await here
      const data = await getSemesterGrades(term);
      setUserGrades(data);
    }
    fetchData();
  };

  useEffect(() => {
    setMarksFromTerm();
  }, [term]);

  return (
    <div>
      <FormControl className={classes.formControl}>
        <InputLabel id="demo-simple-select-label">Oceny semestralne</InputLabel>

        <Select labelId="term" id="term" value={term} onChange={handleChange}>
          <MenuItem value={TERMS.term1}>{TERMS.term1}</MenuItem>
          <MenuItem value={TERMS.term2}>{TERMS.term2}</MenuItem>
        </Select>
      </FormControl>
      {userGrades == null || userGrades?.length === 0
        ? null
        : userGrades?.map((grade) => (
            <Paper my={2} key={grade.subjectName} className={classes.con}>
              <Box p={2}>
                <h2>Przedmiot: {grade.subjectName}</h2>
                <h4>Średnia: {grade.subjectAverage}</h4>
              </Box>
              <div>
                <TableContainer component={Paper}>
                  <Table className={classes.table} aria-label="simple table">
                    <TableHead>
                      <TableRow>
                        <TableCell align="right">Ocena</TableCell>
                        <TableCell align="right">Średnia</TableCell>
                        <TableCell align="right">Średnia semestralna</TableCell>
                      </TableRow>
                    </TableHead>
                    <TableBody>
                      {grade.semesterGrades.map((row) => (
                        <TableRow key={`${row.date}--${grade.subjectName}`}>
                          <TableCell align="right">{row.finalGrade}</TableCell>
                          <TableCell align="right">
                            {row.subjectAverage}
                          </TableCell>
                          <TableCell align="right">
                            {grade.semesterAverage}
                          </TableCell>
                        </TableRow>
                      ))}
                    </TableBody>
                  </Table>
                </TableContainer>
              </div>
            </Paper>
          ))}
    </div>
  );
}

export default UserTermMarks;
