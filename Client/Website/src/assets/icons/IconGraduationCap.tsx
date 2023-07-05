import {SVGProps} from "react";

export default function IconGraduationCap(props: SVGProps<SVGSVGElement>, height?: number, width?: number) {
    return (
        <svg
            viewBox="0 0 1000 1000"
            fill="currentColor"
            height="1em"
            width="1em"
            {...props}
        >
            <path d="M166 612l334 168 276-136c-2.667 14.667-5.333 30.333-8 47s-4.667 28.333-6 35c-1.333 6.667-5 14.333-11 23s-14 16.333-24 23c-10 6.667-25 14-45 22-26.667 12-53.333 25.667-80 41-26.667 15.333-47.667 26.667-63 34-15.333 7.333-28.333 11-39 11-10.667 0-24-4.333-40-13s-37.333-21-64-37-53.333-29.333-80-40c-48-21.333-82.333-44.333-103-69s-36.333-61-47-109m810-246c16 9.333 24 20.333 24 33 0 12.667-8 23.667-24 33l-78 44-308-102c-14.667-24-44.667-36-90-36-26.667 0-49 5.333-67 16s-27 24-27 40 9 29.333 27 40c18 10.667 40.333 16 67 16 17.333 0 29.333-1.333 36-4l292 68-268 152c-40 21.333-80 21.333-120 0L24 432c-16-9.333-24-20.333-24-33 0-12.667 8-23.667 24-33l416-234c40-21.333 80-21.333 120 0l416 234M848 808c12-77.333 16.333-138 13-182-3.333-44-9.667-74-19-90l-14-22 70-38c4 5.333 8 14.667 12 28s9.667 47 17 101 5 119.667-7 197c-2.667 17.333-10 27.333-22 30-12 2.667-23.667 1-35-5-11.333-6-16.333-12.333-15-19" />
        </svg>
    );
}