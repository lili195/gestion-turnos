import { useState, useEffect } from "react";
import "./App.css";
import NavMenu from "./components/gestion-turnos/NavMenu";
import Header from "./components/gestion-turnos/Header";
import Home from "./components/gestion-turnos/Home";
import InitialPage from "./components/gestion-turnos/InitialPage";
import {
  PAGES,
  USER_TYPE,
  SERVICES_BACK,
  KEYCLOAK,
} from "./constants/constants";
import SheduleTurn from "./components/gestion-turnos/SheduleTurn";
import YourTurn from "./components/gestion-turnos/YourTurn";
import CancelTurn from "./components/gestion-turnos/CancelTurn";
import Notifications from "./components/gestion-turnos/Notifications";
import Keycloak from "keycloak-js";
import {
  fetchServiceInfo,
  fetchUsers,
  checkUserShiftByDate,
} from "./api/ServicesApi";

function App() {
  const [keycloak, setKeycloak] = useState(null);
  const [userType, setUserType] = useState(USER_TYPE.ADMIN); // Inicialmente asumimos que es un usuario ADMIN
  const [started, setStarted] = useState(false);
  const [currentPage, setCurrentPage] = useState(PAGES.INITIAL);
  const [currentService, setCurrentService] = useState("");
  const [serviceInfo, setServiceInfo] = useState({});
  const [userName, setUserName] = useState("Pilar");
  const [usersList, setUsersList] = useState([]); // quemado mientras esté el servicio que traiga la lista de usuarios
  const [createdTurn, setCreatedTurn] = useState([]);
  // --------------------------------------ESTO ES NUEVO PERO ES NECESARIO--------------------------------------------------

  const [turnInfo, setTurnInfo] = useState({
    service: currentService,
    dateSelected: "",
    timeOptions: [
      "8:00 a.m.",
      "9:00 a.m.",
      "10:00 a.m.",
      "11:00 a.m.",
      "2:00 p.m.",
      "3:00 p.m.",
      "4:00 p.m.",
      "5:00 p.m.",
    ],
    timeSelected: "",
    userList: usersList,
    dependentList: ["Luis"],
    userSelected: userName,
    dependentSelected: "",
    room: "sala default",
  });

  const handleTurnInfo = (newInfo) => {
    setTurnInfo(newInfo);
  };

  useEffect(() => {
    setTurnInfo((prevTurnInfo) => ({
      ...prevTurnInfo,
      dateSelected: "",
      userList: usersList,
      userSelected: userName,
      service: currentService,
      dependentSelected: "",
      timeSelected: "",
      room: serviceInfo.room,
    }));
  }, [currentService, usersList]);

  useEffect(() => {
    setTurnInfo((prevTurnInfo) => ({
      ...prevTurnInfo,
      userSelected: userName,
    }));
  }, [userName]);

  // ------------------------------------------------COSAS POR COMENTAR-------------------------------------------

  // const handleLogout = () => {
  //   setCurrentService('');
  //   setUserName('');
  //   setStarted(false);
  // };

  // const handleStart = () => {
  //   setStarted(true);
  //   setCurrentPage(PAGES.HOME);
  // };
  // const handleService = (service) => {
  //   setCurrentService(service);
  // };

  // -----------------------------------------------------HASTA ACÁ-------------------------------------------------------

  const handleCurrentPage = (page) => {
    setCurrentPage(page);
  };

  useEffect(() => {
    const keycloakInstance = new Keycloak({
      url: KEYCLOAK.URL,
      realm: KEYCLOAK.REALM,
      clientId: KEYCLOAK.CLIENT_ID,
    });
    setKeycloak(keycloakInstance);
  }, []);

  const handleLogout = () => {
    if (keycloak) {
      keycloak.logout();
      setStarted(false);
      setUserType(USER_TYPE.USER);
      setUserName("");
    }
  };

  const handleStart = () => {
    if (keycloak) {
      keycloak.init({ onLoad: "login-required" }).then((authenticated) => {
        if (authenticated) {
          setStarted(true);
          setUserType(
            keycloak.hasRealmRole(KEYCLOAK.ROLE_ADMIN)
              ? USER_TYPE.ADMIN
              : USER_TYPE.USER
          );
          setUserName(keycloak.tokenParsed.preferred_username);
          setCurrentPage(PAGES.HOME);
          fetch(SERVICES_BACK.TOKEN_SERVICE, {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${keycloak.token}`,
            },
          });
        }
      });
    }
  };

  const handleService = async (service) => {
    setCurrentService(service);
  };

  useEffect(() => {
    const fetchInfo = async () => {
      try {
        if (currentService) {
          const data = await fetchServiceInfo(currentService);
          setServiceInfo(data);
          setTurnInfo((prevTurnInfo) => ({
            ...prevTurnInfo,
            dependentList: [data.dependent] || prevTurnInfo.dependentList,
            room: data.room || prevTurnInfo.room,
          }));
        }
      } catch (error) {
        console.error("Error fetching service info:", error);
      }
    };
    fetchInfo();
  }, [currentService]);

  useEffect(() => {
    if (keycloak !== null && userType === USER_TYPE.ADMIN && currentService !== "") {
      const fetchUsersA = async () => {
        try {
          const users = await fetchUsers(keycloak);
          const usernames = users.map(user => user.username);
          setUsersList(usernames);
        } catch (error) {
          console.error("Error fetching service info:", error);
        }
      };
      fetchUsersA();
    }
  }, [userType, currentService]);


  //------------------------------------Seccion de info del turno----------------------//
  useEffect(() => {
    const fetchTurn = async () => {
      try {
        if (currentPage === PAGES.TURN && currentService) {
          const data = await checkUserShiftByDate(
            userName,
            turnInfo.dateSelected
          );
          setCreatedTurn(data);
        }
      } catch (error) {
        console.error("Error fetching service info:", error);
      }
    };
    fetchTurn();
  }, [currentPage, currentService, userName, turnInfo.dateSelected]);

  return started ? (
    <div>
      <Header />
      <NavMenu
        userType={userType}
        signOut={handleLogout}
        handleCurrentPage={handleCurrentPage}
        handleService={handleService}
        userName={userName}
        currentService={currentService}
      />
      {currentPage === PAGES.HOME && (
        <Home handleService={handleService} handlePage={handleCurrentPage} />
      )}
      {currentPage === PAGES.SHEDULE && (
        <SheduleTurn
          turnInfo={turnInfo}
          handleTurnInfo={handleTurnInfo}
          userType={userType}
          handleCurrentPage={handleCurrentPage}
        />
      )}
      {currentPage === PAGES.TURN && <YourTurn createdTurn={createdTurn} />}
      {currentPage === PAGES.CANCEL && (
        <CancelTurn
          userName={userName}
          handleCurrentPage={handleCurrentPage}
          userType={userType}
        />
      )}
      {currentPage === PAGES.NOTIFICATIONS && (
        <Notifications createdTurn={createdTurn} />
      )}
    </div>
  ) : (
    <InitialPage onStart={handleStart} />
  );
}

export default App;
