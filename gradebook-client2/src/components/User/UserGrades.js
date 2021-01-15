import React, { useEffect } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import {
  Box,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
} from '@material-ui/core';
import { getMarks } from '../../api';

const useStyles = makeStyles((theme) => ({
  con: {
    marginBottom: theme.spacing(2),
  },
}));

export default function UserGrades() {
  const classes = useStyles();
  const [userGrades, setUserGrades] = React.useState([]);

  useEffect(() => {
    async function fetchData() {
      const data = await getMarks();
      setUserGrades(data);
    }
    fetchData();
  }, []);

  return (
    <div>
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
                        <TableCell align="right">waga</TableCell>
                        <TableCell align="right">komentarz</TableCell>
                        <TableCell align="right">data</TableCell>
                      </TableRow>
                    </TableHead>
                    <TableBody>
                      {grade.grades.map((row) => (
                        <TableRow key={`${row.date}--${grade.subjectName}`}>
                          <TableCell align="right">{row.grade}</TableCell>
                          <TableCell align="right">{row.gradeWeight}</TableCell>
                          <TableCell align="right">{row.comment}</TableCell>
                          <TableCell align="right">{row.date}</TableCell>
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
