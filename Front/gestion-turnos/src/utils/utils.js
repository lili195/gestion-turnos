export const formatDate = (date) => {
    if (!(date instanceof Date)) {
      console.error("Invalid date object:", date);
      return "";
    }
    const day = `0${date.getDate()}`.slice(-2);
    const month = `0${date.getMonth() + 1}`.slice(-2);
    const year = date.getFullYear();
    return `${year}-${month}-${day}`;
  };