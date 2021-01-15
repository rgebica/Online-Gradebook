import { Button, makeStyles, TextField } from '@material-ui/core';
import React, { useState } from 'react';
import { addSubject } from '../../api/index';

const useStyles = makeStyles((theme) => ({
  formControl: {
    margin: theme.spacing(1),
    minWidth: 200,
  },
  btn: {
    margin: theme.spacing(1),
  },
  rest: {
    marginLeft: theme.spacing(1),
  },
}));

function AdministratorAddSubject() {
  const [subjectName, setSubjectName] = useState();

  const classes = useStyles();

  const handleAddSubject = () => {
    if (subjectName) {
      addSubject(subjectName).then((res) => {
        if (res === 'Subject Created') {
          setSubjectName('');
          alert('Przedmiot dodany');
        }
      });
    } else {
      alert('Wpisz nazwę przedmiotu');
    }
  };

  return (
    <div className={classes.container}>
      <TextField
        variant="outlined"
        margin="normal"
        required
        fullWidth
        id="subject"
        label="Nazwa przedmiotu"
        name="newPassword"
        value={subjectName}
        onChange={(e) => setSubjectName(e.target.value)}
      />
      <Button
        className={classes.btn}
        variant="contained"
        color="primary"
        onClick={() => handleAddSubject()}
      >
        Dodaj przedmiot
      </Button>
    </div>
  );
}

export default AdministratorAddSubject;
