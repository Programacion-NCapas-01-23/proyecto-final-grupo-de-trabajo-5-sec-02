import React from "react";

function IconSchedule(props: React.SVGProps<SVGSVGElement>) {
    return (
        <svg
            viewBox="0 0 24 24"
            fill="currentColor"
            height="1em"
            width="1em"
            {...props}
        >
            <path d="M12 14a1 1 0 10-1-1 1 1 0 001 1zm5 0a1 1 0 10-1-1 1 1 0 001 1zm-5 4a1 1 0 10-1-1 1 1 0 001 1zm5 0a1 1 0 10-1-1 1 1 0 001 1zM7 14a1 1 0 10-1-1 1 1 0 001 1zM19 4h-1V3a1 1 0 00-2 0v1H8V3a1 1 0 00-2 0v1H5a3 3 0 00-3 3v12a3 3 0 003 3h14a3 3 0 003-3V7a3 3 0 00-3-3zm1 15a1 1 0 01-1 1H5a1 1 0 01-1-1v-9h16zm0-11H4V7a1 1 0 011-1h14a1 1 0 011 1zM7 18a1 1 0 10-1-1 1 1 0 001 1z" />
        </svg>
    );
}

export default IconSchedule;