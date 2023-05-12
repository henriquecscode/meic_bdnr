import React from "react";
import { primaryColor } from "../../utils/colors";

export default function HorizontalRule({ text }) {
  return (
    <div className="d-flex align-items-center mt-5 mb-3">
      <div style={{ flex: 1, backgroundColor: primaryColor, height: "3px" }} />

      <p style={{ margin: "0 10px" }}>{text}</p>

      <div style={{ flex: 1, backgroundColor: primaryColor, height: "3px" }} />
    </div>
  );
}
