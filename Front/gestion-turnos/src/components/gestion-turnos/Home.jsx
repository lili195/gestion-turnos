import React from 'react';
import consultasImage from './turnos-images/imagen_consultas.png';
import pagosImage from './turnos-images/imagen_pagos.png';
import reclamosImage from './turnos-images/imagen_reclamos.png';
import Card from './Card';
import { PAGES, SERVICES } from '../../constants/constants';

const Home = ({ handleService, handlePage }) => {
  return (
    <div className="section">
      <div className="homeDescription">
        <div className="homeTitle">Nuestros servicios</div>
        <div className="homeText">
          ¡Bienvenido a nuestra aplicación de gestión de turnos! <br />{' '}
          Simplificando tu día, uno a la vez
        </div>
      </div>
      <div className="cardsContainer">
        <Card
          title={SERVICES.CONSULTAS}
          image={consultasImage}
          handleService={handleService}
          handlePage={handlePage}
        />
        <Card
          title={SERVICES.PAGOS}
          image={pagosImage}
          handleService={handleService}
          handlePage={handlePage}
        />
        <Card
          title={SERVICES.RECLAMOS}
          image={reclamosImage}
          handleService={handleService}
          handlePage={handlePage}
        />
      </div>
    </div>
  );
};

export default Home;
