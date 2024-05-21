import * as React from 'react';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Modal from '@mui/material/Modal';
import Check from './turnos-images/CheckIcon.svg';
import Equis from './turnos-images/EquisIcon.svg';

const style = {
  position: 'absolute',
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  width: 400,
  bgcolor: 'background.paper',
  border: '2px solid #000',
  boxShadow: 24,
  p: 4,
};

export default function BasicModal({ open, handleOpen, type, message }) {
  //   const [open, setOpen] = React.useState(false);
  //   const handleOpen = () => setOpen(true);
  //   const handleClose = () => setOpen(false);

  return (
    <Modal
      open={open}
      onClose={handleOpen}
      aria-labelledby="modal-modal-title"
      aria-describedby="modal-modal-description"
    >
      <Box className="modalContainer">
        <img
          src={type === 'success' ? Check : Equis}
          className="modalIcon"
          alt="success icon"
        />
        <div className="modalContent" id="modal-modal-description">
          <span className="modalText">
            {message || 'Default message to modal'}
          </span>
          <button className="modalButton" onClick={() => handleOpen()}>
            Aceptar
          </button>
        </div>
      </Box>
    </Modal>
  );
}