import IconGraduationCap from "@/assets/icons/IconGraduationCap";
export const Header = () => {
    // TODO solicitar svg
    return (
        <>
            <nav
                className="">
                <div className="">
                    <a href="#" className="">
                        <IconGraduationCap />
                        <span className="">U-Helper</span>
                    </a>
                    <div className="" id="mobile-menu-2">
                        <ul className="">
                            <li>
                                <a href="#"
                                   className=""
                                   aria-current="page">Login</a>
                            </li>
                            <li>
                                <a href="#"
                                   className="">Registrarse</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
        </>
    )
}