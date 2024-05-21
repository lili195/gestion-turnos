import React from 'react';

const TurnCard = ({ turn }) => {
  return (
    <div className="turnCard">
      <span className="turnCard-text">Tu turno</span>
      <span className="turnCard-number">{turn || 'C-4'}</span>
    </div>
  );
};

export default TurnCard;