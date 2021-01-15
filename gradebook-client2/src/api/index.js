import { getUser } from '../tools/user';
import axios from 'axios';
import { API_HOST, USER_INFORMATION } from './url';

export const getAuthHeader = () => {
  const user = getUser();

  if (user == null) {
    console.error('You did not provided userControlAuth');
    return {};
  }
  return {
    headers: { Authorization: `Bearer ${user.authenticationToken}` },
  };
};

export const getUserInformation = (userId) => {
  if (userId != null) {
    return axios
      .get(`${USER_INFORMATION}/${userId}`)
      .then((response) => response.data)
      .catch((err) => {
        console.log(err);
      });
  }
  const user = getUser();
  return axios
    .get(`${USER_INFORMATION}/${user.userId}`)
    .then((response) => response.data)
    .catch((err) => {
      console.log(err);
    });
};

export const updateUserInformation = (userId, userData) => {
  const withAuthToken = getAuthHeader();
  return axios
    .put(
      `${API_HOST}/user-info/${userId}`,
      {
        ...userData,
      },
      {
        ...withAuthToken,
      }
    )
    .then((response) => response.data)
    .catch((err) => {
      console.log(err);
    });
};

export const getAllTeachers = () => {
  return axios
    .get(API_HOST + '/all-teachers')
    .then((response) => response.data)
    .catch((err) => {
      console.log(err);
    });
};

export const getAllUsers = () => {
  return axios
    .get(API_HOST + '/all-users')
    .then((response) => response.data)
    .catch((err) => {
      console.log(err);
    });
};

export const getAllClasses = () => {
  return axios
    .get(API_HOST + '/classes')
    .then((response) => response.data)
    .catch((err) => {
      console.log(err);
    });
};

export const getAllSubjects = () => {
  return axios
    .get(API_HOST + '/subjects')
    .then((response) => response.data)
    .catch((err) => {
      console.log(err);
    });
};

export const getMarks = () => {
  const user = getUser();
  if (user?.userId != null) {
    return axios
      .get(API_HOST + '/grades/' + user?.userId + '/subjects')
      .then((response) => response.data)
      .catch((err) => {
        console.log(err);
      });
  }
};

export const getUsersFromSubject = (subjectId) => {
  return axios
    .get(API_HOST + '/' + subjectId + '/users')
    .then((response) => response.data)
    .catch((err) => {
      console.log(err);
    });
};

export const getUserSubjectsWithGrades = (userId) => {
  return axios
    .get(API_HOST + '/grades/' + userId + '/subjects')
    .then((response) => response.data)
    .catch((err) => {
      console.log(err);
    });
};

export const getSemesterGrades = (term) => {
  const user = getUser();
  console.log(term);
  if (user?.userId != null) {
    return axios
      .get(
        API_HOST +
          '/semester-grades/' +
          user?.userId +
          `/${term}/subjects?semester=` +
          term
      )
      .then((response) => response.data)
      .catch((err) => {
        console.log(err);
      });
  }
};

export const getSubjects = (childrenId) => {
  const user = getUser();
  if (childrenId != null) {
    return axios
      .get(API_HOST + '/subjects/' + childrenId)
      .then((response) => response.data)
      .catch((err) => {
        console.log(err);
      });
  }
  if (user?.userId != null) {
    return axios
      .get(API_HOST + '/subjects/' + user?.userId)
      .then((response) => response.data)
      .catch((err) => {
        console.log(err);
      });
  }
};

export const getChildrens = () => {
  const user = getUser();
  return axios
    .get(API_HOST + '/parent-children/' + user?.userId)
    .then((response) => response.data)
    .catch((err) => {
      console.log(err);
    });
};

export const getStudents = () => {
  return axios
    .get(API_HOST + '/students/')
    .then((response) => response.data)
    .catch((err) => {
      console.log(err);
    });
};

export const getStudentsBySubject = (subjectId) => {
  return axios
    .get(API_HOST + '/student-subjects/' + subjectId)
    .then((response) => response.data)
    .catch((err) => {
      console.log(err);
    });
};

export const addMark = (markData) => {
  const withAuthToken = getAuthHeader();
  return axios
    .post(
      API_HOST + '/add-grade',
      {
        ...markData,
      },
      {
        ...withAuthToken,
      }
    )
    .then((response) => response.data)
    .catch((err) => {
      console.log(err);
    });
};

export const addFinalMark = (markData) => {
  const withAuthToken = getAuthHeader();
  return axios
    .post(
      API_HOST + '/add-final-grade',
      {
        ...markData,
      },
      {
        ...withAuthToken,
      }
    )
    .then((response) => response.data)
    .catch((err) => {
      console.log(err);
    });
};

export const addPresence = (presenceData) => {
  const withAuthToken = getAuthHeader();
  return axios
    .post(
      API_HOST + '/add-presence',
      {
        ...presenceData,
      },
      {
        ...withAuthToken,
      }
    )
    .then((response) => response.data)
    .catch((err) => {
      console.log(err);
    });
};

export const addBehaviour = (behaviourData) => {
  const withAuthToken = getAuthHeader();
  return axios
    .post(
      API_HOST + '/add-behaviour',
      {
        ...behaviourData,
      },
      {
        ...withAuthToken,
      }
    )
    .then((response) => response.data)
    .catch((err) => {
      console.log(err);
    });
};

export const addResponse = (responseData) => {
  const withAuthToken = getAuthHeader();
  return axios
    .post(
      API_HOST + '/add-response/',
      {
        ...responseData,
      },
      {
        ...withAuthToken,
      }
    )
    .then((response) => response.data)
    .catch((err) => {
      console.log(err);
    });
};

export const addUser = (requestData) => {
  const withAuthToken = getAuthHeader();
  return axios
    .post(
      API_HOST + '/users',
      {
        ...requestData,
      },
      {
        ...withAuthToken,
      }
    )
    .then((response) => response.data)
    .catch((err) => {
      console.log(err);
    });
};

export const addUserToSubject = (requestData) => {
  const withAuthToken = getAuthHeader();
  return axios
    .post(
      API_HOST + '/user-subjects',
      {
        ...requestData,
      },
      {
        ...withAuthToken,
      }
    )
    .then((response) => response.data)
    .catch((err) => {
      console.log(err);
    });
};

export const addClass = (className) => {
  const withAuthToken = getAuthHeader();
  return axios
    .post(
      API_HOST + '/create-class',
      {
        className,
      },
      {
        ...withAuthToken,
      }
    )
    .then((response) => response.data)
    .catch((err) => {
      console.log(err);
    });
};

export const addSubject = (subjectName) => {
  const withAuthToken = getAuthHeader();
  return axios
    .post(
      API_HOST + '/create-subject',
      {
        subjectName,
      },
      {
        ...withAuthToken,
      }
    )
    .then((response) => response.data)
    .catch((err) => {
      console.log(err);
    });
};

export const removeUser = (userId) => {
  const withAuthToken = getAuthHeader();
  return axios
    .delete(`${API_HOST}/user/${userId}`, {
      ...withAuthToken,
    })
    .then((response) => response.data)
    .catch((err) => {
      console.log(err);
    });
};

export const removeGrade = (gradeId) => {
  const withAuthToken = getAuthHeader();
  return axios
    .delete(`${API_HOST}/grades/${gradeId}`, {
      ...withAuthToken,
    })
    .then((response) => response.data)
    .catch((err) => {
      console.log(err);
    });
};

export const resetPassword = (userId) => {
  const withAuthToken = getAuthHeader();
  return axios
    .put(`${API_HOST}/reset-password/${userId}`, {
      ...withAuthToken,
    })
    .then((response) => response.data)
    .catch((err) => {
      console.log(err);
    });
};
