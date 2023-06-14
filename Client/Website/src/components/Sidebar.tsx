import { Header } from "@/components/Header";
import IconDashboard from "@/assets/icons/IconDashboard";
import IconSchool from "@/assets/icons/IconSchool";
import IconStudent from "@/assets/icons/IconStudent";
import IconSchedule from "@/assets/icons/IconSchedule";
import IconBxsBook from "@/assets/icons/IconBxsBook";
import IconCircleUser from "@/assets/icons/IconCircleUser";
import React, {ReactNode} from "react";

// TODO Refactor the Sidebar component to open/close it

export const Sidebar = ({ children }: { children: ReactNode }) => {
    return(
        <>
            <Header/>
            <aside id="logo-sidebar"
                   className=""
                   aria-label="Sidebar">
                <div className="">
                    <h5 id="drawer-navigation-label"
                        className="">Menu</h5>
                    <button type="button" data-drawer-hide="drawer-navigation" aria-controls="drawer-navigation"
                            className="">
                        <svg aria-hidden="true" className="" fill="currentColor" viewBox="0 0 20 20"
                             xmlns="http://www.w3.org/2000/svg">
                            <path fillRule="evenodd"
                                  d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z"
                                  clipRule="evenodd"></path>
                        </svg>
                        <span className="">Close menu</span>
                    </button>
                    <ul className="">
                        <li>
                            <a href="#"
                               className="">
                                <IconDashboard className="" />
                                <span className="ml-3">Dashboard</span>
                            </a>
                        </li>
                        <li>
                            <a href="#"
                               className="">
                                <IconSchool className="" />
                                <span className="">Facultades</span>
                            </a>
                        </li>
                        <li>
                            <a href="#"
                               className="">
                                <IconStudent className="" />
                                <span className="">Carreras y Pemsun</span>
                            </a>
                        </li>
                        <li>
                            <a href="#"
                               className="">
                                <IconSchedule className="" />
                                <span className="">Horarios</span>
                            </a>
                        </li>
                        <li>
                            <a href="#"
                               className="">
                                <IconBxsBook className="" />
                                <span className="">Materias</span>
                            </a>
                        </li>
                        <li>
                            <a href="#"
                               className="">
                                <IconCircleUser className="" />
                                <span className="">Mi Perfil</span>
                            </a>
                        </li>
                    </ul>
                </div>
            </aside>
            {children}
        </>
    )
}