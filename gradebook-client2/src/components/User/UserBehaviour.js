import React, { useEffect, useState } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import axios from 'axios';
import { API_HOST } from '../../api/url';
import { getUser } from '../../tools/user';
import {
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  TextField,
} from '@material-ui/core';
import { addResponse } from '../../api';

const useStyles = makeStyles((theme) => ({
  con: {
    marginBottom: theme.spacing(2),
  },
  dialogForm: {
    display: 'flex',
    flexDirection: 'column',
  },
}));

export default function UserBehaviour({ userId, asParent }) {
  const classes = useStyles();
  const [behaviourData, setBehaviourData] = React.useState([]);
  const [open, setOpen] = React.useState(false);
  const [behaviourId, setBehaviourId] = useState();
  const [responseContent, setResponseContent] = useState();
  const [topic, setTopic] = useState();

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
    init();
  };

  const handleAddResponse = () => {
    addResponse({
      behaviourId,
      responseContent,
      subject: topic,
    }).then((response) => {
      if (response === 'Response sent') {
        handleClose();
        setTopic('');
        setResponseContent('');
        alert('Dodano odpowiedź');
      } else {
        console.error(response);
      }
    });
  };

  const init = () => {
    let user = null;
    if (userId != null) {
      user = userId;
    } else {
      user = getUser().userId;
    }
    if (user) {
      axios
        .get(API_HOST + '/user-behaviours/' + user)
        .then((response) => {
          if (response?.data && response?.data?.length) {
            setBehaviourData(response?.data[0]);
          }
        })
        .catch((err) => {
          console.log(err);
        });
    }
  };

  useEffect(() => {
    if (open) {
      // setStatus(editablePresence?.status);
      // setPresence(editablePresence?.presence);
    }
  }, [open]);

  useEffect(() => {
    init();
  }, [userId]);

  return (
    <div>
      <Paper my={2} className={classes.con}>
        <TableContainer component={Paper}>
          <Table className={classes.table} aria-label="simple table">
            <TableHead>
              <TableRow>
                <TableCell align="right">Ocena</TableCell>
                <TableCell align="right">Opis</TableCell>
                <TableCell align="right">Data</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {behaviourData == null ||
              behaviourData?.length === 0 ||
              behaviourData?.behaviours == null ||
              behaviourData?.behaviours?.length === 0
                ? null
                : behaviourData?.behaviours?.map((row) => (
                    <TableRow
                      key={`${row.date}--${row.description}-${row.grade}`}
                    >
                      <TableCell align="right">{row.grade}</TableCell>
                      <TableCell align="right">{row.description}</TableCell>
                      <TableCell align="right">{row.date}</TableCell>
                      {asParent && (
                        <TableCell align="center">
                          <Button
                            variant="outlined"
                            color="primary"
                            onClick={() => {
                              setBehaviourId(row.behaviourId);
                              handleClickOpen();
                            }}
                          >
                            Edytuj
                          </Button>
                        </TableCell>
                      )}
                    </TableRow>
                  ))}
            </TableBody>
          </Table>
        </TableContainer>
      </Paper>

      <Dialog
        open={open}
        onClose={handleClose}
        aria-labelledby="alert-dialog-title"
        aria-describedby="alert-dialog-description"
      >
        <DialogTitle id="alert-dialog-title">{'Edytuj obecność'}</DialogTitle>
        <DialogContent>
          <DialogContentText
            style={{ width: '500px', display: 'flex', flexDirection: 'column' }}
            id="alert-dialog-description"
          >
            <form className={classes.dialogForm} noValidate autoComplete="off">
              <TextField
                id="topc"
                label="Temat"
                value={topic}
                onChange={(e) => setTopic(e.target.value)}
              />
              <TextField
                multiline
                rows={4}
                id="responseContent"
                label="Odpowiedź"
                value={responseContent}
                onChange={(e) => setResponseContent(e.target.value)}
              />
            </form>

            <Button
              variant="contained"
              color="primary"
              onClick={handleAddResponse}
            >
              Zapisz
            </Button>
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose} color="primary">
            Zamknij
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}
