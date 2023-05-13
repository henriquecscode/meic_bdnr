import { BsPlus, BsX } from "react-icons/bs";
import { FcRating } from "react-icons/fc";
import Button from "react-bootstrap/Button";
import OverlayTrigger from "react-bootstrap/OverlayTrigger";
import Popover from "react-bootstrap/Popover";
import getImageSrc from "../../utils/utils";
import Image from "react-bootstrap/Image";

export default function FriendCard({
  friend,
  index,
  withIcon = false,
  showLevel = false,
  voteInfo = null,
  handleAddFriend = () => {},
  handleRemoveFriend = () => {},
}) {
  const votePopover = (info) => (
    <Popover id="popover-basic">
      <Popover.Header
        as="h3"
        className="bg-white"
      >{`Vote: ${info}`}</Popover.Header>
    </Popover>
  );

  const getVoteButton = (info) => {
    return info ? (
      <OverlayTrigger
        trigger="click"
        placement="left"
        overlay={votePopover(info)}
        rootClose
      >
        <Button className="rounded-circle" variant="link" size="sm">
          <FcRating size={20} />
        </Button>
      </OverlayTrigger>
    ) : null;
  };

  return (
    <div key={index} className="d-flex justify-content-between">
      <div className="d-flex">
        <Image
          src={getImageSrc(friend.picture)}
          alt={friend.username}
          className="thumbnail-image profile-image me-3"
          height={40}
          width={40}
        />

        <div className="d-flex flex-column justify-content-center">
          <p className="mb-1">{friend.username}</p>
          {showLevel ? (
            <p className="text-muted">{`Friend ${
              friend.level !== -1 ? friend.level + "º" : "--"
            } level`}</p>
          ) : null}
        </div>
      </div>

      {withIcon ? (
        friend.level !== 1 ? (
          <BsPlus
            size={20}
            onClick={(event) => handleAddFriend(event, friend.username)}
          />
        ) : (
          <BsX
            size={20}
            onClick={(event) => handleRemoveFriend(event, friend.username)}
          />
        )
      ) : null}

      {voteInfo ? getVoteButton(voteInfo) : null}
    </div>
  );
}
