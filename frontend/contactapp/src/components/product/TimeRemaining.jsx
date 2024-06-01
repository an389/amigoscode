import React, { useEffect, useState } from 'react';
import { Text } from '@chakra-ui/react';


function TimeRemaining({ endDate, startDate }) {
    const whenWillStart = (new Date(Date.now()) - new Date(startDate)) < 0;
    const calculateTimeLeft = () => {
        const difference = new Date(endDate) - new Date(startDate);
        let timeLeft = {};

        if (difference > 0) {
            timeLeft = {
                days: Math.floor(difference / (1000 * 60 * 60 * 24)),
                hours: Math.floor((difference / (1000 * 60 * 60)) % 24),
                minutes: Math.floor((difference / 1000 / 60) % 60),
                seconds: Math.floor((difference / 1000) % 60)
            };
        }

        return timeLeft;
    };

    const [timeLeft, setTimeLeft] = useState(calculateTimeLeft());

    useEffect(() => {
        const timer = setTimeout(() => {
            setTimeLeft(calculateTimeLeft());
        }, 1000);

        return () => clearTimeout(timer);
    });

    const timerComponents = [];

    Object.keys(timeLeft).forEach(interval => {
        if (!timeLeft[interval]) {
            return;
        }

        timerComponents.push(
            <span key={interval}>
                {timeLeft[interval]} {interval}{" "}
            </span>
        );
    });

    if (whenWillStart){
        return <>
        </>
    }

    return (
        <Text color={'green.800'}>
            Remain: {(timerComponents.length ? timerComponents : <span>Time's up!</span>)}
        </Text>
    );
}


export default TimeRemaining;
