// import React from 'react';

// const Notifications = () => {
//   return <div className="section">Notifications section</div>;
// };

// export default Notifications;
import React from 'react';
import TurnCard from './TurnCard';

const TurnInfoCard = ({ turnData, currentTurn }) => {
  return (
    <div className="turnInfoCardContainer">
      <div className="turnInfo-card">
        <span className="turnInfo-cardText">Turno actual</span>
        <span className="turnInfo-cardNumber">{currentTurn || ''}</span>
      </div>
      <div className="turnInfo-content">
        <div className="turnInfo-content-up">
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

const Notifications = ({createdTurn}) => {
  const isNotEmptyTurn = createdTurn.length > 0
  const addScript = (input) => {
    const partes = input.match(/^([A-Za-z]+)(\d+)$/);
    return partes ? `${partes[1]}-${partes[2]}` : input;
  };
  return (
    <div className="section yourTurnSection">
      <span className="turnInfoSection-title">
        Tu turno se acerca. Preparate!
      </span>
      {isNotEmptyTurn && <TurnCard turn={addScript(createdTurn[0].shiftName)} />}
      {isNotEmptyTurn && <TurnInfoCard turnData={createdTurn[0]} currentTurn={addScript('M1')} />}
    </div>
  );
};

export default Notifications;
