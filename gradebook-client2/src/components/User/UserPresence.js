import React, { useEffect, useState } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import axios from 'axios';
import { API_HOST } from '../../api/url';
import { getUser } from '../../tools/user';

import {
  Box,
  Button,
  Checkbox,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
  FormControl,
  FormControlLabel,
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
import { STATUSES } from '../Teacher/TeacherAddPresence.js';

const useStyles = makeStyles((theme) => ({
  con: {
    marginBottom: theme.spacing(2),
  },
  green: {
    background: 'green',
    border: '1px solid silver',
  },
  red: {
    background: 'red',
    border: '1px solid silver',
  },
  white: {
    background: 'white',
    border: '1px solid silver',
  },
  presence: {
    margin: '0 auto',
    width: '20px',
    height: '20px',
  },
}));

export default function UserPresence({ userId, subjectId, editable }) {
  const classes = useStyles();
  const [presenceData, setPresenceData] = React.useState([]);
  const [open, setOpen] = React.useState(false);
  const [editablePresence, setEditablePresence] = React.useState();
  const [status, setStatus] = useState();
  const [presence, setPresence] = useState();

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
    init();
  };

  const handleUpdatePresence = () => {
    axios
      .put(API_HOST + '/presence/' + editablePresence?.presenceId, {
        presence,
        status,
      })
      .then((response) => {
        if (response.status === 200) {
          handleClose();
        } else {
          console.error(response);
        }
      })
      .catch((err) => {
        console.log(err);
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
        .get(API_HOST + '/presence/' + user + '/subjects')
        .then((response) => {
          setPresenceData(response?.data);
        })
        .catch((err) => {
          console.log(err);
        });
    }
  };

  useEffect(() => {
    init();
  }, [userId]);

  useEffect(() => {
    if (open) {
      setStatus(editablePresence?.status);
      setPresence(editablePresence?.presence);
    }
  }, [open]);

  return (
    <div>
      {presenceData == null || presenceData?.length === 0
        ? null
        : presenceData
            ?.filter((item) =>
              subjectId == null ? true : item.subjectId === subjectId
            )
            .map(
              ({
                presence,
                subjectName,
                presencePercentage,
                presenceCounter,
                absenceCounter,
              }) => (
                <Paper my={2} key={subjectName} className={classes.con}>
                  <Box p={2}>
                    <h2>Przedmiot: {subjectName}</h2>
                    <h4>Liczba obecności: {presenceCounter}</h4>
                    <h4>Liczba nieobecności: {absenceCounter}</h4>
                    <h4>Procent: {presencePercentage + '%'}</h4>
                  </Box>
                  <div>
                    <TableContainer component={Paper}>
                      <Table
                        className={classes.table}
                        aria-label="simple table"
                      >
                        <TableHead>
                          <TableRow>
                            <TableCell align="center">Obecność</TableCell>
                            <TableCell align="center">Data</TableCell>
                            <TableCell align="center">Status</TableCell>
                            {editable && (
                              <TableCell align="center">Edytuj</TableCell>
                            )}
                          </TableRow>
                        </TableHead>
                        <TableBody>
                          {presence == null || presence?.length === 0
                            ? null
                            : presence.map((row) => (
                                <TableRow key={row.date}>
                                  <TableCell align="center">
                                    <div
                                      className={`${classes.presence} ${
                                        row.presence
                                          ? classes.green
                                          : classes.red
                                      }`}
                                    ></div>
                                  </TableCell>
                                  <TableCell align="center">
                                    {row.date}
                                  </TableCell>
                                  <TableCell align="center">
                                    {row.status}
                                  </TableCell>
                                  {editable && (
                                    <TableCell align="center">
                                      <Button
                                        small
                                        variant="outlined"
                                        color="primary"
                                        onClick={() => {
                                          setEditablePresence(row);
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
                  </div>
                </Paper>
              )
            )}
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
            <FormControl className={classes.formControl}>
              <InputLabel id="demo-simple-select-label">Status</InputLabel>
              <Select
                labelId="term"
                id="term"
                value={status}
                onChange={(e) => setStatus(e.target.value)}
              >
                {Object.keys(STATUSES)
                  .filter(
                    (stat) =>
                      !presence ||
                      (presence &&
                        (stat === STATUSES.OBECNY ||
                          stat === STATUSES.USPRAWIEDLIWIONY))
                  )
                  .map((status, idx) => (
                    <MenuItem value={status} key={`${status}--${idx}`}>
                      {status}
                    </MenuItem>
                  ))}
              </Select>
            </FormControl>
            <FormControl className={classes.formControl}>
              <FormControlLabel
                control={
                  <Checkbox
                    checked={presence}
                    name="presence"
                    color="primary"
                    onChange={(e) => setPresence(e.target.checked)}
                  />
                }
                label="Obecność"
              />
            </FormControl>
            <Button
              variant="contained"
              color="primary"
              onClick={handleUpdatePresence}
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
