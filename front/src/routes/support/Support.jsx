import {Col, Container, Row} from "react-bootstrap";
import styles from "./Support.module.css";
import React, {useEffect, useState} from "react";

import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import InputGroup from 'react-bootstrap/InputGroup';

import userImage from '../../images/anon_img1.png';
import suppImage from '../../images/anon_img2.png';
import {MenuItem, MenuList, Paper} from "@mui/material";
import axios from "axios";
import {GET_THREAD, GET_THREADS, POST_MESSAGE, POST_THREAD} from "../../constants/URLS";
import Markdown from "react-markdown";

const Support = () => {
    const [selectedThread, setSelectedThread] = useState(0);
    const [selectedIndex, setSelectedIndex] = useState(-1);
    const [threads, setThreads] = useState([]);

    useEffect(() => {
        getThreads();
    }, []);

    const scrollToBottom = (id) => {
        const element = document.getElementById(id);
        element.scrollTop = element.scrollHeight;
    }

    const addThread = () => {
        axios.post(POST_THREAD)
            .then(() => {
                getThreads();
            })
            .catch(error => {
                console.error(error);
            })
    }

    const getThreads = () => {
        axios.get(GET_THREADS)
            .then(response => {
                setThreads(response.data.data);
            })
            .catch(error => {
                console.error(error);
            });
    }

    const [newMessage, setNewMessage] = useState("");
    const [messages, setMessages] = useState([]);

    useEffect(() => {
        scrollToBottom("message-chart-scroll");
    }, [messages]);

    const getInitialMessage = () => {
        return createBotMessage(
            new Date().toLocaleTimeString(),
            "Напишите ваше сообщение в чат..."
        );
    }

    const createUserMessage = (createdTimestamp, message) => {
        return {
            createdTimestamp: createdTimestamp,
            message: message,
            author: "Вы",
            img: userImage
        }
    }

    const createBotMessage = (createdTimestamp, message) => {
        return {
            createdTimestamp: createdTimestamp,
            message: message,
            author: "Бот",
            img: suppImage
        }
    }

    const getThread = (threadId) => {
        axios.get(GET_THREAD + threadId)
            .then(response => {
                let newThreads = [...threads];
                let threadIndex = newThreads.findIndex(value => {
                    return Number(threadId) ===
                        Number(value.aiThreadId)
                });
                newThreads[threadIndex] = response.data;
                setThreads(newThreads);
            })
            .catch(error => {
                console.log(error);
            })
    }

    const sendMessage = async (threadId, messageStr) => {
        const messagePost = {
            message: messageStr
        }
        const time = new Date().toLocaleTimeString();
        const userMessage = createUserMessage(time, messageStr);
        await axios.post(POST_MESSAGE[0] + threadId + POST_MESSAGE[1], messagePost)
            .then(response => {
                const botMessage = createBotMessage(response.data.createdTimestamp, response.data.message);
                setMessages([...messages, userMessage, botMessage]);
            })
            .catch(error => {
                console.error(error);
            })
        await getThread(threadId);
    }

    const handleMessageFormChange = (e) => {
        setNewMessage(e.target.value);
    }
    const handleSendMessage = (e) => {
        e.preventDefault();
        sendMessage(selectedThread, newMessage);
    }

    return (
        <Container className="">
            <Row>
                <Col className="col-2">
                    <Container className="mt-3">
                        <Paper>
                            <MenuList>
                                {threads.map((value, index) => {
                                    return (
                                        <MenuItem key={value.aiThreadId} selected={index === selectedIndex}
                                                  data-ai-thread-id={value.aiThreadId}
                                                  style={{justifyContent: "flex-end"}}
                                                  onClick={(e) => {
                                                      setSelectedIndex(index);
                                                      setSelectedThread(value.aiThreadId);
                                                      let aiMessages = threads
                                                          .find(value => {
                                                              return Number(e.target.dataset.aiThreadId) ===
                                                                  Number(value.aiThreadId)
                                                          })
                                                          .aiMessages
                                                          .map(aiMessage => {
                                                              return aiMessage.role === "assistant" ?
                                                                  createBotMessage(aiMessage.createdTimestamp, aiMessage.message) :
                                                                  createUserMessage(aiMessage.createdTimestamp, aiMessage.message);
                                                          });
                                                      setMessages([getInitialMessage(), ...aiMessages]);
                                                  }}
                                        >
                                            {value.aiMessages.length !== 0 ?
                                                value.aiMessages[0].message.substring(0, 8) + ".." :
                                                "..."}
                                        </MenuItem>
                                    )
                                })}
                            </MenuList>
                        </Paper>
                        <div className="mt-3" style={{display: "flex", justifyContent: "end", alignItems: "center"}}>
                            <Button variant="primary" onClick={addThread}>Новый тред</Button>
                        </div>
                    </Container>
                </Col>
                <Col>
                    <Container className="h-100">
                        <Row>
                            <Col className={styles.chatMessages + " p-4"}>
                                <Row>
                                    <div id="message-chart-scroll" className={styles.messageChart}>
                                        {messages.map((message, index) => {
                                            return (
                                                <div className="chat-message-right pb-4" key={index}>
                                                    <div>
                                                        <img src={message.img}
                                                             className="rounded-circle mr-1" width="40"
                                                             height="40" alt={"Message"}/>
                                                        <div
                                                            className="text-muted small text-nowrap mt-2">{message.createdTimestamp}</div>
                                                    </div>
                                                    <div className="flex-shrink-1 bg-light rounded py-2 px-3 mr-3">
                                                        <div
                                                            className={styles.nickname + " mb-1"}>{message.author}</div>
                                                        <Markdown>
                                                            {message.message}
                                                        </Markdown>
                                                    </div>
                                                </div>
                                            )
                                        })}
                                    </div>
                                </Row>

                                <InputGroup className="mb-3">
                                    <Form.Control
                                        value={newMessage}
                                        onChange={handleMessageFormChange}
                                        placeholder="Введите ваше сообщение"
                                        aria-label="Recipient's username"
                                        aria-describedby="basic-addon2"
                                    />
                                    <Button variant="outline-secondary" onClick={handleSendMessage} id="button-addon2">
                                        Отправить
                                    </Button>
                                </InputGroup>
                            </Col>
                        </Row>
                    </Container>
                </Col>
            </Row>
        </Container>
    )
}

export default Support;
