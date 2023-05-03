import { BsPlus, BsX } from "react-icons/bs";
import getImageSrc from "../../utils/utils";
import Image from "react-bootstrap/Image";

export default function FriendCard({ friend, index, withIcon = false, showLevel = false }) {
  return <div key={index} className="d-flex justify-content-between">
    <div className="d-flex">
      <Image
        src={getImageSrc(friend.picture)}
        alt={friend.username}
        className="thumbnail-image profile-image me-3"
        height={40}
        width={40}
      />
      <div>
        <p className="mb-1">{friend.username}</p>
        {showLevel ? <p className="text-muted">{`Friend ${friend.level}º level`}</p> : null}
      </div>
    </div>
    {withIcon ? (friend.level > 1 ? <BsPlus size={20} /> : <BsX size={20} />) : null}
  </div>;
}