import { Button, makeStyles, TextField } from '@material-ui/core';
import React, { useState } from 'react';
import { addClass } from '../../api/index';

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

function AdministratorAddClass() {
  const [newClassName, setNewClassName] = useState();

  const classes = useStyles();

  const handleAddSubject = () => {
    if (newClassName) {
      addClass(newClassName).then((res) => {
        if (res === 'Class created') {
          setNewClassName('');
          alert('Klasa dodana');
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
        label="Nazwa klasy"
        name={'class'}
        value={newClassName}
        onChange={(e) => setNewClassName(e.target.value)}
      />
      <Button
        className={classes.btn}
        variant="contained"
        color="primary"
        onClick={() => handleAddSubject()}
      >
        Dodaj klasę
      </Button>
    </div>
  );
}

export default AdministratorAddClass;
