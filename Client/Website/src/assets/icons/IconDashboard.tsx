import React from "react";

function IconDashboard(props: React.SVGProps<SVGSVGElement>) {
    return (
        <svg
            viewBox="0 0 24 24"
            fill="currentColor"
            height="1em"
            width="1em"
            {...props}
        >
            <path d="M2 5v14h20V5H2m18 7h-4V7h4v5m-6-2h-4V7h4v3m-4 2h4v5h-4v-5M4 7h4v10H4V7m12 10v-3h4v3h-4z" />
        </svg>
    );
}

export default IconDashboard;