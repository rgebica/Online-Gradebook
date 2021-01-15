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
import { getSubjects, getUsersFromSubject, removeGrade } from '../../api/index';
import '../style.css';
import Popper from '@material-ui/core/Popper';

const useStyles = makeStyles((theme) => ({
  formControl: {
    margin: theme.spacing(1),
    minWidth: 200,
    fontFamily: 'Montserrat',
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
  const [marks, setMarks] = useState();

  const handleChangeSubject = (event) => {
    setSelectedSubject(event.target.value);
  };

  const classes = useStyles();

  const setFetchedSubjects = () => {
    async function fetchData() {
      const data = await getSubjects();
      setSubjects(data?.subjects);
    }

    fetchData();
  };
  const setFetchedMarks = () => {
    async function fetchData() {
      const data = await getUsersFromSubject(selectedSubject);
      setMarks(data);
    }

    fetchData();
  };

  useEffect(() => {
    setFetchedSubjects();
  }, []);
  useEffect(() => {
    setFetchedMarks();
  }, [selectedSubject]);

  // popper
  const handleRemoveGrade = () => {
    removeGrade(selectedGradeId)
      .then((res) => {
        setAnchorEl(null);
        setFetchedMarks();
      })
      .catch((err) => {
        console.error(err);
      });
  };

  const [anchorEl, setAnchorEl] = React.useState(null);
  const [selectedGradeId, setSelectedGradeId] = React.useState();

  const handleClickPopper = (event, gradeId) => {
    event.preventDefault();
    setAnchorEl(anchorEl ? null : event.currentTarget);
    setSelectedGradeId(gradeId);
  };
  const open = Boolean(anchorEl);
  const id = open ? 'simple-popper' : undefined;

  return (
    <body>
      <div className={classes.container} font-family>
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
                    <MenuItem
                      key={subject.subjectName}
                      value={subject.subjectId}
                    >
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
                  <TableCell align="right">Waga 5</TableCell>
                  <TableCell align="right">Waga 4</TableCell>
                  <TableCell align="right">Waga 3</TableCell>
                  <TableCell align="right">Waga 2</TableCell>
                  <TableCell align="right">Waga 1</TableCell>
                  <TableCell align="right">średnia ważona</TableCell>
                  <TableCell align="right">Ocena początkowa</TableCell>
                  <TableCell align="right">Średnia roczna</TableCell>
                  <TableCell align="right">Ocena końcowa</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {marks?.map((mark, markIdx) => (
                  <TableRow
                    key={`${markIdx}--${mark.subjectName}-${mark.firstName} ${mark.lastName}`}
                  >
                    <TableCell
                      key={`${mark.firstName} ${mark.lastName}`}
                      align="right"
                    >{`${mark.firstName} ${mark.lastName}`}</TableCell>
                    {[5, 4, 3, 2, 1].map((row) => {
                      return (
                        <>
                          <TableCell align="right" key={`${row}-tablecell`}>
                            {mark.grades
                              ?.filter(({ gradeWeight }) => gradeWeight === row)
                              .map(
                                ({ grade, gradeId }, gradeIdx, gradeArray) => (
                                  <>
                                    <span
                                      aria-describedby={id}
                                      onClick={(e) =>
                                        handleClickPopper(e, gradeId)
                                      }
                                    >
                                      {grade}
                                      {gradeIdx < gradeArray.length - 1 && ', '}
                                    </span>
                                  </>
                                )
                              )}
                          </TableCell>
                          <Popper
                            id={id}
                            open={open}
                            anchorEl={anchorEl}
                            key={`${row}-Popper`}
                          >
                            <div className={classes.paper}>
                              <Button
                                className={classes.btn}
                                variant="contained"
                                color="primary"
                                onClick={handleRemoveGrade}
                              >
                                Usuń ocenę
                              </Button>
                            </div>
                          </Popper>
                        </>
                      );
                    })}

                    <TableCell
                      key={mark.subjectAverage + 'subjectAverage' + markIdx}
                      align="right"
                    >
                      {mark.subjectAverage}
                    </TableCell>
                    <TableCell
                      key={mark.semesterGrade + 'semesterGrade' + markIdx}
                      align="right"
                    >
                      {mark.semesterGrade}
                    </TableCell>
                    <TableCell
                      key={mark.yearAverage + 'yearAverage' + markIdx}
                      align="right"
                    >
                      {mark.yearAverage}
                    </TableCell>
                    <TableCell
                      key={mark.finalGrade + 'finalGrade' + markIdx}
                      align="right"
                    >
                      {mark.finalGrade}
                    </TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </TableContainer>
        </div>
      </div>
    </body>
  );
}

export default TeacherMarks;
