import React, {useEffect, useState} from 'react';
import './App.css';
import axios from 'axios';
import Selector from "./Selector";

let baseURL = "http://localhost:8080/api"

// if (process.env.NODE_ENV === 'production') {
//     console.log("Running in docker container")
//     baseURL = "http://factory:8080/api"
// }

const myAxios = axios.create({baseURL})

interface Order {
    orderId: number
    handlebarType: string
    handlebarMaterial: string
    handlebarGearshift: string
    handleMaterial: string
    deliveryDate: number
}

const App: React.FC = () => {


    const [pending, setPending] = useState<boolean>(false)
    const [handlebarType, setHandlebarType] = useState<string>()
    const [handlebarMaterial, setHandlebarMaterial] = useState<string>()
    const [handlebarGearshift, setHandlebarGearshift] = useState<string>()
    const [handleType, setHandleType] = useState<string>()
    const [order, setOrder] = useState<Order>()
    const [handlebarTypes, setHandlebarTypes] = useState<string[]>()
    const [handlebarMaterials, setHandlebarMaterials] = useState<string[]>()
    const [handlebarGearshifts, setHandlebarGearshifts] = useState<string[]>()
    const [handleTypes, setHandleTypes] = useState<string[]>()


    useEffect(() => {
        myAxios.get('handlebarType')
            .then((res) => {
                setHandlebarTypes(res.data)
                setPending(false)
            })
        setPending(true)
    }, [])

    if (pending) return (<>Loading ...</>)

    if (!handlebarType && handlebarTypes) {
        return (
            <>
                <Selector list={handlebarTypes} submit={(option: string) => {
                    setHandlebarType(option)
                    myAxios.get('handlebarMaterial?handlebarType=' + option)
                        .then((res) => {
                            setHandlebarMaterials(res.data)
                            setPending(false)
                        })
                }}/>
            </>
        )
    }
    if (!handlebarMaterial && handlebarMaterials) {
        return (
            <>
                <Selector list={handlebarMaterials}
                          submit={(option: string) => {
                              setHandlebarMaterial(option)
                              myAxios.get('handlebarGearshift?handlebarType=' + handlebarType + '&handlebarMaterial=' + option)
                                  .then((res) => {
                                      setHandlebarGearshifts(res.data)
                                  })
                          }}/>
            </>
        )
    }
    if (!handlebarGearshift && handlebarGearshifts) {
        return (
            <>
                <Selector list={handlebarGearshifts}
                          submit={(option: string) => {
                              setHandlebarGearshift(option)
                              myAxios.get('handleType?handlebarType=' + handlebarType + '&handlebarMaterial=' + handlebarMaterial + '&handlebarGearshift=' + option)
                                  .then((res) => {
                                          setHandleTypes(res.data)
                                          setPending(false)
                                      }
                                  )
                          }}
                />
            </>
        )
    }

    if (!handleType && handleTypes) {
        return (
            <>
                <Selector last={true} list={handleTypes} submit={(option: string) => {
                    setHandleType(option)
                    console.log("Verifying")
                    myAxios.post('verify', {
                        handlebarType,
                        handlebarMaterial,
                        handlebarGearshift,
                        handleType: option
                    })
                        .then((res) => {
                            setOrder(res.data)
                        })
                }
                }/>
            </>
        )
    }

    if (order === undefined) return <p>Loading</p>
    else return (
        <>
            <div>{order.orderId}</div>
            <div>{order.handlebarType}</div>
            <div>{order.handlebarMaterial}</div>
            <div>{order.handlebarGearshift}</div>
            <div>{order.handleMaterial}</div>
            <div>{new Date(order.deliveryDate).toDateString()}</div>
        </>
    )
}

export default App;
