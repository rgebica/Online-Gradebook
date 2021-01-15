import {
  FormControl,
  InputLabel,
  makeStyles,
  MenuItem,
  Select,
} from '@material-ui/core';
import React, { useEffect, useState } from 'react';
import { getChildrens } from '../../api/index';
import UserBehaviour from '../User/UserBehaviour';

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

function ParentBehaviour() {
  const [students, setStudents] = useState();
  const [selectedStudent, setSelectedStudent] = useState();

  const handleChangeStudent = (event) => {
    setSelectedStudent(event.target.value);
  };

  const classes = useStyles();

  const setFetchedStudents = () => {
    async function fetchData() {
      const data = await getChildrens();
      setStudents(data.children);
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
      </div>
      {selectedStudent != null && (
        <UserBehaviour asParent userId={selectedStudent} />
      )}
    </div>
  );
}

export default ParentBehaviour;
