import userIcon from "../assets/user-profile-icon.jpg";

function getImageSrc(picture) {
  let src;
  try {
    src = require(picture);
  }
  catch (err) {
    src = userIcon;
  }

  return src;
}

export default getImageSrc;