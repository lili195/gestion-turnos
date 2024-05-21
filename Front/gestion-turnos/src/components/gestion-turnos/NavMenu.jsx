import React from 'react';
import userIcon from './turnos-images/userIcon.svg';
import adminIcon from './turnos-images/adminIcon.svg';
import logOutIcon from './turnos-images/logOut.svg';
import calendarIcon from './turnos-images/calendar.svg';
import listIcon from './turnos-images/list.svg';
import stopIcon from './turnos-images/stop.svg';
import notificationIcon from './turnos-images/notifications.svg';
import homeIcon from './turnos-images/homeIcon.svg';

import { USER_TYPE, PAGES } from '../../constants/constants';

const NavMenu = ({
  userType,
  signOut,
  handleCurrentPage,
  handleService,
  userName,
}) => {
  return (
    <div className="menuContainer">
      <ul className="menuList">
        <li
          className="menuItem"
          onClick={() => {
            handleCurrentPage(PAGES.HOME);
          }}
        >
          <img className="menuIcon" src={homeIcon} />
          <span>Inicio</span>
        </li>
        <li
          className="menuItem"
          onClick={() => handleCurrentPage(PAGES.SHEDULE)}
        >
          <img className="menuIcon" src={calendarIcon} />
          <span>Crear turno</span>
        </li>
        {userType === USER_TYPE.USER && (
          <li
            className="menuItem"
            onClick={() => handleCurrentPage(PAGES.TURN)}
          >
            <img className="menuIcon" src={listIcon} />
            <span>Ver turno</span>
          </li>
        )}
        <li
          className="menuItem"
          onClick={() => handleCurrentPage(PAGES.CANCEL)}
        >
          <img className="menuIcon" src={stopIcon} />
          <span>Cancelar turno</span>
        </li>
        {userType === USER_TYPE.USER && (
          <li
            className="menuItem"
            onClick={() => handleCurrentPage(PAGES.NOTIFICATIONS)}
          >
            <img className="menuIcon" src={notificationIcon} />
            <span>Notificaciones</span>
          </li>
        )}
      </ul>
      <ul className="menuList logOut">
        <li className="menuItem">
          {userType === 'user' && <img className="menuIcon" src={userIcon} />}
          {userType === 'admin' && <img className="menuIcon" src={adminIcon} />}
          <span>{userName}</span>
        </li>
        <li
          className="menuItem"
          onClick={() => {
            signOut();
            handleCurrentPage('initialPage');
            handleService('');
          }}
        >
          <img className="menuIcon" src={logOutIcon} />
          <span>Salir</span>
        </li>
      </ul>
    </div>
  );
};

export default NavMenu;
