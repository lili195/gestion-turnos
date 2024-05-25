import React, { useState, useEffect } from "react";
import ReactDatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import "bootstrap/dist/css/bootstrap.min.css";
import { FaCalendarAlt } from "react-icons/fa";
import TurnCard from "./TurnCard";
import BasicModal from "./BasicModal";
import { PAGES, USER_TYPE } from "../../constants/constants";
import { checkUserShiftByDate, deleteShiftById, getShiftsByDate } from "../../api/ServicesApi";

const TurnSearch = ({ handleSelectDate, showCardInfo }) => {
  const [preselectDate, setPreselectDate] = useState(new Date());

  const formatDate = (date) => {
    const day = `0${date.getDate()}`.slice(-2);
    const month = `0${date.getMonth() + 1}`.slice(-2);
    const year = date.getFullYear();
    return `${year}-${month}-${day}`;
  };

  const handleDate = (date) => {
    setPreselectDate(date);
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

  const handleSubmit = (e) => {
    e.preventDefault();
    handleSelectDate(formatDate(preselectDate));
    showCardInfo();
  };

  return (
    <div className="turnSearchSection">
      <form className="turnSearch-form" onSubmit={handleSubmit}>
        <div className="turnSearch-calendar">
          <span className="turnSearch-span">Busca la fecha del turno:</span>
          <label>
            <ReactDatePicker
              selected={preselectDate}
              onChange={handleDate}
              minDate={new Date()}
              maxDate={new Date().setDate(new Date().getDate() + 30)}
              className="formItem-input smallInput"
              required
              customInput={<CustomCalendarInput value={preselectDate} />}
            />
          </label>
        </div>
        <button className="turnSearch-button" type="submit">
          Buscar
        </button>
      </form>
    </div>
  );
};

const TurnToCancel = ({ turnData }) => {
  return (
    <div className="turnToCancelSection">
      <TurnCard turn={turnData.shiftName} />
      <div className="turnToCancel-content">
        <div className="turnToCancel-section1">
          <div className="turnInfo-item">
            <span className="turnInfo-title">Fecha</span>
            <span className="turnInfo-info">{turnData.date}</span>
          </div>
          <div className="turnInfo-item">
            <span className="turnInfo-title">Hora</span>
            <span className="turnInfo-info">{turnData.time}</span>
          </div>
        </div>
        <div className="turnToCancel-section2">
          <div className="turnInfo-item">
            <span className="turnInfo-title">Sala</span>
            <span className="turnInfo-info">{turnData.room}</span>
          </div>
          <div className="turnInfo-item">
            <span className="turnInfo-title">Servicio</span>
            <span className="turnInfo-info">{turnData.service}</span>
          </div>
        </div>
        <div className="turnInfo-item">
          <span className="turnInfo-title">Dependiente</span>
          <span className="turnInfo-info">{turnData.dependent}</span>
        </div>
      </div>
    </div>
  );
};

const CancelTurn = ({ userName, handleCurrentPage, userType }) => {
  const [dateSelected, setDateSelected] = useState("");
  const [turnSelected, setTurnSelected] = useState([]);
  const [showTurnCard, setShowTurnCard] = useState(false);
  const [openModal, setOpenModal] = useState(false);
  const [isCanceled, setIsCanceled] = useState(false);
  const [message, setMessage] = useState("");
  const isNotEmptyTurnData = turnSelected.length > 0;
  const [emptyTurnModal, setEmptyTurnModal] = useState(
    showTurnCard && !isNotEmptyTurnData
    //true
  );

  const handleOpenModal = () => {
    setOpenModal(!openModal);
  };

  const handleCloseModal = () => {
    setOpenModal(!openModal);
    handleCurrentPage(PAGES.HOME);
  };

  const handleShowTurnCard = () => {
    setShowTurnCard(!showTurnCard);
  };

  const resetDate = () => {
    setDateSelected("");
    setShowTurnCard(false);
  };

  const handleSelectDate = (date) => {
    setDateSelected(date);
  };

  const handleCancelTurn = () => {
    deleteTurn();
    handleOpenModal();
    resetDate();
  };

  const handleCloseEmptyTurnModal = () => {
    setEmptyTurnModal(!emptyTurnModal);
  };


  useEffect(() => {
    if(userType === USER_TYPE.ADMIN){
      const fetchTurn = async () => {
        try {
          const data = await getShiftsByDate(dateSelected);
          setTurnSelected(data);
        } catch (error) {
          console.error("Error fetching service info:", error);
        }
      };
      fetchTurn();
    } else {
    const fetchTurn = async () => {
      try {
        const data = await checkUserShiftByDate(userName, dateSelected);
        setTurnSelected(data);
      } catch (error) {
        console.error("Error fetching service info:", error);
      }
    };
    fetchTurn();
    }
  }, [userName, dateSelected, userType]);
  console.log("turn selected: ", turnSelected)

  const deleteTurn = async () => {
    try {
      const deleted = await deleteShiftById(turnSelected[0].id);
      if (deleted) {
        setIsCanceled(deleted);
        setMessage("El turno se canceló exitosamente");
        return;
      }
    } catch (error) {
      console.error("Error fetching service info:", error);
    }
  };

  return (
    <div className="section cancelTurnSection">
      <h1 className="cancelTurnSection-title">Cancelar turno</h1>
      {!showTurnCard && (
        <TurnSearch
          handleSelectDate={handleSelectDate}
          showCardInfo={handleShowTurnCard}
        />
      )}
      {showTurnCard && isNotEmptyTurnData && (
        <TurnToCancel turnData={turnSelected[0]} />
      )}
      {showTurnCard && isNotEmptyTurnData &&(
        <button className="cancelTurnButton" onClick={() => handleCancelTurn()}>
          Cancelar turno
        </button>
      )}
      <BasicModal
        open={openModal}
        handleOpen={handleCloseModal}
        type={isCanceled === true ? "success" : "error"}
        message={message}
      />
      <BasicModal
        open={emptyTurnModal}
        handleOpen={handleCloseEmptyTurnModal}
        type={"error"}
        message={
          "No hay un turno asignado a su nombre en la fecha especificada"
        }
      />
    </div>
  );
};

export default CancelTurn;
