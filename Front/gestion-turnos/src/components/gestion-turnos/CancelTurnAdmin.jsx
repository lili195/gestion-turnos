import React, { useState, useEffect } from "react";
import DataTable from "react-data-table-component";

const CancelTurnAdmin = ({ turnList, handleCancelAdminTurn }) => {
  const [listOfTurn, setListOfTurn] = useState([]);
  const [selectedId, setSelectedId] = useState(null);

  const columns = [
    { name: "Usuario", selector: (row) => row.user },
    { name: "Servicio", selector: (row) => row.service },
    { name: "Sala", selector: (row) => row.room },
    { name: "Dependiente", selector: (row) => row.dependent },
    { name: "Hora", selector: (row) => row.time },
  ];

  useEffect(() => {
    setListOfTurn(turnList);
  }, [turnList]);

  const handleSelectedRowsChange = ({ selectedRows }) => {
    const id = selectedRows.length > 0 ? selectedRows[0].id : null;
    setSelectedId(id);
  };


  return (
    <div className="cancelTurnAdmin-container">
      <DataTable
        columns={columns}
        data={listOfTurn.length > 0 ? listOfTurn : []}
        selectableRows
        selectableRowsSingle
        onSelectedRowsChange={handleSelectedRowsChange}
      />
      <div className="centerButton">
        <button className="cancelTurnButton" onClick={()=> handleCancelAdminTurn(selectedId)}>Cancelar Turno</button>
      </div>
      
    </div>
  );
};

export default CancelTurnAdmin;
