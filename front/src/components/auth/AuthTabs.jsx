import Box from "@mui/material/Box";
import {Tabs} from "@mui/material";
import Tab from "@mui/material/Tab";
import * as React from "react";
import PropTypes from "prop-types";
import {Login} from "./login/Login";
import {Registration} from "./registration/Registration";

export const AuthTabs = ({setOpen}) => {
    const [value, setValue] = React.useState(0);

    const handleChange = (event, newValue) => {
        setValue(newValue);
    };

    function CustomTabPanel(props) {
        const {children, value, index, ...other} = props;

        return (
            <div
                role="tabpanel"
                hidden={value !== index}
                id={`simple-tabpanel-${index}`}
                aria-labelledby={`simple-tab-${index}`}
                {...other}
            >
                {value === index && children}
            </div>
        );
    }

    CustomTabPanel.propTypes = {
        children: PropTypes.node,
        index: PropTypes.number.isRequired,
        value: PropTypes.number.isRequired,
    };

    function a11yProps(index) {
        return {
            id: `simple-tab-${index}`,
            'aria-controls': `simple-tabpanel-${index}`,
        };
    }

    return (
        <Box sx={{width: '100%'}}>
            <Box sx={{borderBottom: 1, borderColor: 'divider'}}>
                <Tabs indicatorColor="secondary" text-color="secondary" value={value} onChange={handleChange} aria-label="basic tabs example">
                    <Tab label="Логин" {...a11yProps(0)} />
                    <Tab label="Регистрация" {...a11yProps(1)} />
                </Tabs>
            </Box>
            <CustomTabPanel value={value} index={0}>
                <Login setOpen={setOpen}/>
            </CustomTabPanel>
            <CustomTabPanel value={value} index={1}>
                <Registration/>
            </CustomTabPanel>
        </Box>
    )
}
