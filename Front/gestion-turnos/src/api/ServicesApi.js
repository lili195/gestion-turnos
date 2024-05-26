import { SERVICES_BACK } from '../constants/constants';

export const fetchServiceInfo = async (serviceType) => {
    try {
        const response = await fetch(`${SERVICES_BACK.SERVICES_INFO}/${serviceType}`);
        if (!response.ok) {
            throw new Error(`Error en la petición: ${response.statusText}`);
        }
        const data = await response.json();
        return data;
    } catch (error) {
        console.error('Hubo un problema con el fetch:', error);
        throw error;
    }
};

export const fetchUsers = async (keycloak) => {
    try {
        const response = await fetch(SERVICES_BACK.GET_USERS, {
            method: "GET",
            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${keycloak.token}`,
            },
        });
        if (!response.ok) {
            throw new Error(`Error en la petición: ${response.statusText}`);
        }
        const data = await response.json();
        return data;
    } catch (error) {
        console.log('Hubo un problema con el fetch:', error);
        throw error;
    }
};

export const checkUserShiftByDate = async (user, date) => {
    const response = await fetch(`${SERVICES_BACK.CHECK_SHIFT}/user/${user}/date/${date}`);
    if (!response.ok) {
        throw new Error("Failed to check user turn by date");
    }
    return response.json();
};

export const getShiftsByDate = async (date) => {
    const response = await fetch(`${SERVICES_BACK.CHECK_SHIFT}/date/${date}`);
    if (!response.ok) {
        throw new Error(`Error en la petición de turnos: ${response.statusText}`);
    }
    const data = await response.json();
    return data;
};

export const checkShiftAvailability = async (date, time, service) => {
    const response = await fetch(`${SERVICES_BACK.CHECK_SHIFT}/${date}/${time}/${service}`);
    if (!response.ok) {
      throw new Error("Failed to check turn availability");
    }
    return response.json();
};

export const createShift = async (shift) => {
    const response = await fetch(SERVICES_BACK.CREATE_SHIFT, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(shift),
    });
    if (!response.ok) {
      throw new Error("Failed to create turn");
    }
    return response.json();
};

export const deleteShiftById = async (id) => {
    const response = await fetch(`${SERVICES_BACK.DELETE_SHIFT}/${id}`, {
        method: 'DELETE',
    });
    if (!response.ok) {
        throw new Error('Failed to delete shift');
    }
    const result = await response.json();
    return result;
}