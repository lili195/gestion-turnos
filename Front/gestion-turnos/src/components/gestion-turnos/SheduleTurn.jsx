import React, { useState, useEffect } from 'react';
import ReactDatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import { FaCalendarAlt } from 'react-icons/fa';
import { USER_TYPE } from '../../constants/constants';
import {
  checkUserShiftByDate,
  checkShiftAvailability,
  createShift,
} from '../../api/ServicesApi';

const SheduleTurn = ({ turnInfo, handleTurnInfo, userType }) => {
  const [message, setMessage] = useState('');

  // Primero armamos el turno acá antes de mandarlo al app.js
  const [formData, setFormData] = useState({
    date: new Date(),
    time: turnInfo.timeSelected,
    user: turnInfo.userSelected,
    dependent: turnInfo.dependent,
  });

  const resetData = () => {
    setFormData((prevData) => ({
      ...prevData,
      date: new Date(),
      time: '',
      user: '',
      dependent: '',
    }));
  };

  useEffect(() => {
    resetData();
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleSelectDate = (date) => {
    setFormData((prevData) => ({
      ...prevData,
      date: date,
    }));
  };

  const formatDate = (date) => {
    const day = `0${date.getDate()}`.slice(-2);
    const month = `0${date.getMonth() + 1}`.slice(-2);
    const year = date.getFullYear();
    return `${year}-${month}-${day}`;
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    handleTurnInfo((prevTurnInfo) => ({
      ...prevTurnInfo,
      dateSelected: formatDate(formData.date),
      timeSelected: formData.time,
      userSelected: formData.user === '' ? turnInfo.userSelected : formData.user,
      dependentSelected: formData.dependent,
    }));
    resetData();
  };

  const CustomCalendarInput = ({ value, onClick }) => (
    <div className="input-group">
      <input
        type="text"
        className="form-control formItem-input"
        value={value}
        onClick={onClick}
        readOnly
      />
      <div>
        <FaCalendarAlt className="input-group-icon" />
      </div>
    </div>
  );

  useEffect(() => {
    const handleScheduleTurn = async () => {
      const user = turnInfo.userSelected;
      const date = turnInfo.dateSelected;
      const time = turnInfo.timeSelected;
      const service = turnInfo.service;
      const dependent = turnInfo.dependentSelected;
      try {
        // Verificar que el usuario no tenga un turno para esa fecha
        const userTurns = await checkUserShiftByDate(user, date);
        if (userTurns.length > 0) {
          setMessage('You already have a turn for this date.');
          return;
        }

        // Verificar disponibilidad de fecha, hora y dependiente
        const availability = await checkShiftAvailability(date, time, service);
        if (availability) {
          setMessage('The selected time is not available.');
          return;
        }

        // Crear el turno
        const turn = {
          user,
          date,
          time,
          service,
          dependent,
          room: turnInfo.room,
        };
        await createShift(turn);
        setMessage('Turn scheduled successfully.');
      } catch (error) {
        setMessage(`Error: ${error.message}`);
      }

    };

    const isNotEmptyTurnInfo =
      turnInfo.dateSelected !== undefined &&
      turnInfo.dateSelected !== '' &&
      turnInfo.userSelected !== undefined &&
      turnInfo.userSelected !== '' &&
      turnInfo.time !== undefined &&
      turnInfo.time !== '';

    //SOLO ES QUITAR EL && false DE LA SIGUIENTE LINEA PARA QUE SE EJECUTE EL HANDLE
    if (turnInfo) {
      handleScheduleTurn();
    }
  }, [turnInfo]);

  return (
    <div className="section sheduleSection">
      <h1 className="sheduleTurnSection-title">
        Solicitar turno para un usuario
      </h1>
      <div className="sheduleTurnSection-content">
        <h2 className="sheduleTurnSection-subtitle">
          Servicio de {turnInfo.service}
        </h2>
        <form className="sheduleTurnSection-form" onSubmit={handleSubmit}>
          <div className="sheduleTurnSection-form-up">
            <div className="formItem">
              <span className="formItem-span">Fecha:</span>
              <label>
                <ReactDatePicker
                  selected={formData.date}
                  onChange={handleSelectDate}
                  minDate={new Date()}
                  maxDate={new Date().setDate(new Date().getDate() + 30)}
                  className="formItem-input smallInput"
                  required
                  customInput={
                    <CustomCalendarInput
                      value={formData.date.toLocaleDateString()}
                    />
                  }
                />
              </label>
            </div>
            <div className="formItem">
              <span className="formItem-span">Hora:</span>
              <select
                className="formItem-input smallInput"
                name="time"
                value={formData.time}
                onChange={handleChange}
                required
              >
                <option value="" disabled>
                  Selecciona una opción
                </option>
                {turnInfo.timeOptions.map((option) => (
                  <option key={option} value={option}>
                    {option}
                  </option>
                ))}
              </select>
            </div>
          </div>
          <div className="sheduleTurnSection-form-down">
            {userType === USER_TYPE.ADMIN && (
              <div className="formItem">
                <span className="formItem-span">Selecciona el usuario:</span>
                <select
                  className="formItem-input bigInput"
                  name="user"
                  value={formData.user}
                  onChange={handleChange}
                  required
                >
                  {turnInfo.userList.length > 1 && (
                    <option value="" disabled>
                      Selecciona un usuario
                    </option>
                  )}
                  {/* <option value="" disabled>
                    Selecciona un usuario
                  </option> */}
                  {turnInfo.userList.map((user) => (
                    <option key={user} value={user}>
                      {user}
                    </option>
                  ))}
                </select>
              </div>
            )}
            <div className="formItem">
              <span className="formItem-span">
                Selecciona el dependiente que atenderá:
              </span>
              <select
                className="formItem-input bigInput"
                name="dependent"
                value={formData.dependent}
                onChange={handleChange}
                required
              >
                <option value="" disabled>
                  Selecciona un dependiente
                </option>
                {turnInfo.dependentList.map((dependent) => (
                  <option key={dependent} value={dependent}>
                    {dependent}
                  </option>
                ))}
              </select>
            </div>
          </div>
          <button className="sheduleTurnSection-button" type="submit">
            Crear turno
          </button>
        </form>
      </div>
    </div>
  );
};

export default SheduleTurn;